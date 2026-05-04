package com.lmuro.boqez.presentation.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.core.networking.onError
import com.lmuro.boqez.core.utils.Gesture
import com.lmuro.boqez.core.utils.WebSocketMessageType
import com.lmuro.boqez.data.local.GameStateCache
import com.lmuro.boqez.data.remote.dto.socket.SocketGestureResponse
import com.lmuro.boqez.data.remote.services.WSService
import com.lmuro.boqez.domain.model.ActiveGesture
import com.lmuro.boqez.domain.model.LocalPlayer
import com.lmuro.boqez.domain.repository.BoqezRepository
import com.lmuro.boqez.presentation.base.BaseViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class GameViewModel(
    gameStateCache: GameStateCache,
    private val repository: BoqezRepository,
    private val wsService: WSService,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<GameState, GameEvent>() {

    override val initialState: GameState = GameState()

    private val gestureJobs = mutableMapOf<String, Job>()

    init {
        val args = savedStateHandle.toRoute<Screen.GameScreen>()
        val gameStartState = gameStateCache.get(args.gameId)
        gameStartState?.let { start ->
            state.update { currentState ->
                currentState.copy(
                    hand = start.teams
                        .flatMap { it.players }
                        .filterIsInstance<LocalPlayer>()
                        .firstOrNull()
                        ?.hand ?: emptyList(),
                    gameType = start.gameType,
                    teams = start.teams,
                    userId = args.userId,
                    currentPlayerId = start.currentPlayerId,
                    deck = start.deck,
                    discardPile = start.discardPile,
                    trumpSuit = start.trumpSuit,
                    bottomCard = start.bottomCard
                )
            }
        }
        observeMessages()
    }

    override fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.OnCallCards -> TODO()
            is GameEvent.OnGesture -> sendGesture(event.gesture)
            GameEvent.OnLeaveGame -> TODO()
            is GameEvent.OnPlayCard -> TODO()
            is GameEvent.OnSwapCards -> TODO()
        }
    }

    private fun observeMessages() {
        viewModelScope.launch {
            wsService.messages.collect { message ->
                when (message.type) {
                    WebSocketMessageType.GESTURE -> {
                        val data = Json.decodeFromJsonElement<SocketGestureResponse>(message.payload)
                        showGesture(data.userId, data.gesture)
                    }
                    else -> Napier.v("Unhandled socket message.")
                }
            }
        }
    }

    private fun showGesture(userId: String, gesture: Gesture) {
        gestureJobs[userId]?.cancel()

        state.update {
            it.copy(activeGestures = it.activeGestures + (userId to ActiveGesture(gesture)))
        }

        gestureJobs[userId] = viewModelScope.launch {
            delay(2000)
            state.update {
                it.copy(activeGestures = it.activeGestures - userId)
            }
            gestureJobs.remove(userId)
        }
    }

    private fun sendGesture(gesture: Gesture) {
        viewModelScope.launch {
            repository.sendGesture(
                gameId = state.value.roomCode,
                gesture = gesture
            ).onError { networkError, message ->
                val decide = message ?: networkError.toString()
                _snackBarChannel.send(decide)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        gestureJobs.values.forEach { it.cancel() }
        gestureJobs.clear()
    }

}