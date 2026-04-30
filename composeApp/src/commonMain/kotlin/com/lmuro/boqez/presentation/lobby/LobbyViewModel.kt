package com.lmuro.boqez.presentation.lobby

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.core.navigation.navTypes.lobbyScreenTypeMap
import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.core.networking.onError
import com.lmuro.boqez.core.networking.onSuccess
import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.WebSocketMessageType
import com.lmuro.boqez.data.remote.dto.socket.SocketGameStartResponse
import com.lmuro.boqez.data.remote.dto.socket.SocketGameTypeResponse
import com.lmuro.boqez.data.remote.dto.socket.SocketOwnerTransferResponse
import com.lmuro.boqez.data.remote.dto.socket.SocketTeamChangeResponse
import com.lmuro.boqez.data.remote.dto.socket.SocketUserReadyResponse
import com.lmuro.boqez.data.remote.dto.socket.SocketUserResponse
import com.lmuro.boqez.data.remote.mappers.toTeam
import com.lmuro.boqez.data.remote.services.WSService
import com.lmuro.boqez.domain.model.LobbyUser
import com.lmuro.boqez.domain.repository.BoqezRepository
import com.lmuro.boqez.presentation.base.BaseViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class LobbyViewModel(
    private val repository: BoqezRepository,
    private val navigator: Navigator,
    private val wsService: WSService,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<LobbyState, LobbyEvent>() {
    override val initialState: LobbyState = LobbyState()

    override fun onEvent(event: LobbyEvent) {
        when (event) {
            LobbyEvent.OnKickCancel -> TODO()
            LobbyEvent.OnKickConfirm -> TODO()
            is LobbyEvent.OnKickPlayer -> TODO()
            LobbyEvent.OnLeaveGame -> leaveLobby()
            LobbyEvent.OnReadyChange -> changeReady()
            LobbyEvent.OnStartGame -> startGame()
            is LobbyEvent.OnTeamChange -> {
                if (event.teamId == null) leaveTeam()
                else changeTeam(event.teamId)
            }

            is LobbyEvent.OnChangeGameType -> changeGameType(event.gameType)
        }
    }

    init {
        val args = savedStateHandle.toRoute<Screen.LobbyScreen>(typeMap = lobbyScreenTypeMap)
        val players = args.players.ifEmpty {
            listOf(
                LobbyUser(
                    userId = args.userId,
                    username = args.username,
                    isReady = true,
                    teamId = null
                )
            )
        }
        state.update {
            it.copy(
                lobbyId = args.lobbyId,
                players = players,
                gameType = args.gameType,
                ownerId = args.ownerId,
                userId = args.userId
            )
        }
        connectToLobby(args.userId)
        observeMessages()
    }

    private fun connectToLobby(userId: String) {
        viewModelScope.launch {
            wsService.connect(userId)
        }
    }

    private fun observeMessages() {
        viewModelScope.launch {
            wsService.messages.collect { message ->
                when (message.type) {
                    WebSocketMessageType.PLAYER_JOINED -> {
                        val data = Json.decodeFromJsonElement<SocketUserResponse>(message.payload)
                        state.update {
                            it.copy(
                                players = state.value.players + LobbyUser(
                                    userId = data.userId,
                                    username = data.username,
                                    isReady = false,
                                    teamId = null
                                )
                            )
                        }
                    }

                    WebSocketMessageType.PLAYER_LEFT -> {
                        val data = Json.decodeFromJsonElement<SocketUserResponse>(message.payload)
                        state.update { current ->
                            current.copy(
                                players = current.players.filter { player ->
                                    player.userId != data.userId
                                }
                            )
                        }
                    }

                    WebSocketMessageType.OWNER_LEFT -> {
                        val data =
                            Json.decodeFromJsonElement<SocketOwnerTransferResponse>(message.payload)
                        state.update { current ->
                            current.copy(
                                players = current.players.filter { player ->
                                    player.userId != data.oldOwnerId
                                },
                                ownerId = data.newOwnerId
                            )
                        }
                    }
//                    WebSocketMessageType.OWNER_CHANGED -> TODO()
                    WebSocketMessageType.PLAYER_JOINED_TEAM -> {
                        val data =
                            Json.decodeFromJsonElement<SocketTeamChangeResponse>(message.payload)
                        state.update { current ->
                            current.copy(
                                players = current.players.map { player ->
                                    if (player.userId == data.userId) player.copy(
                                        teamId = data.teamId,
                                        isReady = false
                                    )
                                    else player
                                }
                            )
                        }
                    }

                    WebSocketMessageType.PLAYER_LEFT_TEAM -> {
                        val data = Json.decodeFromJsonElement<SocketUserResponse>(message.payload)
                        state.update { current ->
                            current.copy(
                                players = current.players.map { player ->
                                    if (player.userId == data.userId) player.copy(
                                        teamId = null,
                                        isReady = false
                                    )
                                    else player
                                }
                            )
                        }
                    }

                    WebSocketMessageType.PLAYER_READY -> {
                        val data =
                            Json.decodeFromJsonElement<SocketUserReadyResponse>(message.payload)
                        state.update { current ->
                            current.copy(
                                players = current.players.map { player ->
                                    if (player.userId == data.userId) player.copy(isReady = data.isReady)
                                    else player
                                }
                            )
                        }
                    }

                    WebSocketMessageType.GAME_TYPE_CHANGE -> {
                        val data =
                            Json.decodeFromJsonElement<SocketGameTypeResponse>(message.payload)
                        state.update {
                            it.copy(
                                gameType = data.gameType,
                                players = it.players.map { player ->
                                    player.copy(
                                        teamId = null,
                                        isReady = false
                                    )
                                }
                            )
                        }
                    }
//                    WebSocketMessageType.KICK_PLAYER -> TODO()
                    WebSocketMessageType.START_GAME -> {
                        val data =
                            Json.decodeFromJsonElement<SocketGameStartResponse>(message.payload)
                        viewModelScope.launch {
                            navigator.navigateTo(
                                destination = Screen.GameScreen(
                                    gameId = data.gameId
                                )
                            )
                        }
                    }

                    else -> {
                        Napier.v("Unhandled socket message.")
                    }
                }
            }
        }
    }

    private fun leaveTeam() {
        viewModelScope.launch {
            repository.leaveTeam(
                lobbyId = state.value.lobbyId
            ).onError { networkError, message ->
                val decide = message ?: networkError.toString()
                _snackBarChannel.send(decide)
            }
        }
    }

    private fun changeTeam(teamId: Int) {
        viewModelScope.launch {
            repository.changeTeam(
                lobbyId = state.value.lobbyId,
                teamId = teamId
            ).onError { networkError, message ->
                val decide = message ?: networkError.toString()
                _snackBarChannel.send(decide)
            }
        }
    }

    private fun changeReady() {
        viewModelScope.launch {
            val thisPlayer = state.value.players.find { player ->
                player.userId == state.value.userId
            }

            if (thisPlayer == null) {
                _snackBarChannel.send("Weird, you're not in this lobby.")
                return@launch
            }

            repository.changeReady(
                lobbyId = state.value.lobbyId,
                isReady = !thisPlayer.isReady
            ).onError { networkError, message ->
                val decide = message ?: networkError.toString()
                _snackBarChannel.send(decide)
            }
        }
    }

    private fun leaveLobby() {
        viewModelScope.launch {
            repository.leaveLobby(
                lobbyId = state.value.lobbyId
            ).onSuccess {
                navigator.navigateUp()
            }.onError { networkError, message ->
                val decide = message ?: networkError.toString()
                _snackBarChannel.send(decide)
            }
        }
    }

    private fun changeGameType(gameType: GameType) {
        viewModelScope.launch {
            repository.changeGameType(
                lobbyId = state.value.lobbyId,
                gameType = gameType
            ).onError { networkError, message ->
                val decide = message ?: networkError.toString()
                _snackBarChannel.send(decide)
            }
        }
    }

    private fun startGame() {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            repository.startGame(lobbyId = state.value.lobbyId)
                .onError { networkError, message ->
                val decide = message ?: networkError.toString()
                _snackBarChannel.send(decide)
            }
            state.update { it.copy(isLoading = false) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            wsService.disconnect()
        }
    }
}