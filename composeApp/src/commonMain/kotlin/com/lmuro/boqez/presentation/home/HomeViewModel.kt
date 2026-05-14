package com.lmuro.boqez.presentation.home

import androidx.lifecycle.viewModelScope
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.core.networking.onError
import com.lmuro.boqez.core.networking.onSuccess
import com.lmuro.boqez.core.utils.Const.LOBBY_CODE_LENGTH
import com.lmuro.boqez.data.local.DataStoreApi
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.ACTIVE_GAME_ID
import com.lmuro.boqez.data.local.GameStateCache
import com.lmuro.boqez.domain.model.GameStartData
import com.lmuro.boqez.domain.repository.BoqezRepository
import com.lmuro.boqez.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BoqezRepository,
    private val navigator: Navigator,
    private val dataStoreApi: DataStoreApi,
    private val gameStateCache: GameStateCache,
) : BaseViewModel<HomeState, HomeEvent>() {
    override val initialState: HomeState = HomeState()
    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnAboutUsClick -> TODO()
            HomeEvent.OnPlayClick -> {
                state.update {
                    it.copy(
                        shouldShowLobbyDialog = true
                    )
                }
            }

            HomeEvent.OnProfileClick -> TODO()
            HomeEvent.OnSettingsClick -> TODO()
            HomeEvent.OnDissmissDialog -> {
                state.update {
                    it.copy(
                        shouldShowLobbyDialog = false
                    )
                }
            }

            is HomeEvent.OnLobbyCodeChanged -> {
                val code = event.code.take(LOBBY_CODE_LENGTH)
                state.update {
                    it.copy(
                        lobbyCode = code.uppercase()
                    )
                }
            }

            HomeEvent.OnLobbyCreate -> createLobby()
            HomeEvent.OnLobbyJoin -> joinLobby()
            HomeEvent.OnRejoinGame -> rejoinGame()
            HomeEvent.OnDismissRejoin -> {
                viewModelScope.launch {
                    dataStoreApi.delete(ACTIVE_GAME_ID)
                }
                state.update { it.copy(showRejoinDialog = false, pendingGameId = "") }
            }
        }

    }

    init {
        loadUserData()
        checkForActiveGame()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            repository.getUser()
                .onSuccess { res ->
                    state.update {
                        it.copy(
                            username = res.username,
                            userId = res.userId
                        )
                    }
                }.onError { error, message ->
                    val decide = message ?: error.toString()
                    _snackBarChannel.send(decide)
                }
        }
    }

    private fun createLobby() {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            repository.createLobby(
                isPublic = true,
                gameType = null
            ).onSuccess { lobbyId ->
                navigator.navigateTo(
                    destination = Screen.LobbyScreen(
                        lobbyId = lobbyId,
                        userId = state.value.userId,
                        ownerId = state.value.userId,
                        username = state.value.username
                    )
                )
            }.onError { error, message ->
                val decide = message ?: error.toString()
                _snackBarChannel.send(decide)
            }
            state.update { it.copy(isLoading = false, shouldShowLobbyDialog = false) }
        }
    }

    private fun joinLobby() {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            repository.joinLobby(
                lobbyId = state.value.lobbyCode.uppercase()
            ).onSuccess { res ->
                navigator.navigateTo(
                    destination = Screen.LobbyScreen(
                        lobbyId = res.lobbyId,
                        players = res.players,
                        gameType = res.gameType,
                        ownerId = res.ownerId,
                        userId = state.value.userId,
                        username = state.value.username
                    )
                )
            }.onError { error, message ->
                val decide = message ?: error.toString()
                _snackBarChannel.send(decide)
            }
            state.update { it.copy(isLoading = false, shouldShowLobbyDialog = false) }
        }
    }

    private fun checkForActiveGame() {
        viewModelScope.launch {
            val gameId = dataStoreApi.read(ACTIVE_GAME_ID) ?: return@launch
            state.update {
                it.copy(
                    showRejoinDialog = true,
                    pendingGameId = gameId
                )
            }
        }
    }

    private fun rejoinGame() {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            repository.rejoinGame(state.value.userId)
                .onSuccess { data ->
                    val gameData = GameStartData(
                        gameType = data.gameType,
                        teams = data.teams,
                        currentPlayerId = data.currentPlayerId,
                        deck = data.deck,
                        discardPile = data.discardPile,
                        trumpSuit = data.trumpSuit,
                        bottomCard = data.bottomCard
                    )
                    gameStateCache.put(data.gameId, gameData)
                    navigator.navigateTo(
                        destination = Screen.GameScreen(
                            gameId = data.gameId,
                            userId = state.value.userId
                        )
                    ) {
                        popUpTo<Screen.ROOT>()
                    }
                }.onError { error, message ->
                    dataStoreApi.delete(ACTIVE_GAME_ID)
                    val decide = message ?: error.toString()
                    _snackBarChannel.send(decide)
                }
            state.update { it.copy(isLoading = false, showRejoinDialog = false) }
        }
    }
}