package com.lmuro.boqez.presentation.lobby

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.core.navigation.navTypes.lobbyScreenTypeMap
import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.core.utils.WebSocketMessageType
import com.lmuro.boqez.data.remote.dto.socket.SocketUserResponse
import com.lmuro.boqez.data.remote.services.WSService
import com.lmuro.boqez.domain.model.LobbyUser
import com.lmuro.boqez.domain.repository.BoqezRepository
import com.lmuro.boqez.presentation.base.BaseViewModel
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
            LobbyEvent.OnCancelGame -> TODO()
            LobbyEvent.OnKickCancel -> TODO()
            LobbyEvent.OnKickConfirm -> TODO()
            is LobbyEvent.OnKickPlayer -> TODO()
            LobbyEvent.OnLeaveGame -> TODO()
            LobbyEvent.OnReadyChange -> TODO()
            LobbyEvent.OnStartGame -> TODO()
            is LobbyEvent.OnTeamChange -> TODO()
        }
    }

    init {
        val args = savedStateHandle.toRoute<Screen.LobbyScreen>(typeMap = lobbyScreenTypeMap)
        val players = args.players.ifEmpty {
            args.players + LobbyUser(
                userId = args.userId,
                username = args.username,
                isReady = true,
                teamId = null
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
//                    WebSocketMessageType.PLAYER_LEFT -> TODO()
//                    WebSocketMessageType.OWNER_LEFT -> TODO()
//                    WebSocketMessageType.OWNER_CHANGED -> TODO()
//                    WebSocketMessageType.PLAYER_JOINED_TEAM -> TODO()
//                    WebSocketMessageType.PLAYER_LEFT_TEAM -> TODO()
//                    WebSocketMessageType.PLAYER_READY -> TODO()
//                    WebSocketMessageType.GAME_TYPE_CHANGE -> TODO()
//                    WebSocketMessageType.KICK_PLAYER -> TODO()
                    else -> Unit
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            wsService.disconnect()
        }
    }
}