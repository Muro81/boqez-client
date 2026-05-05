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
import com.lmuro.boqez.data.remote.dto.socket.SocketPlayCardResponse
import com.lmuro.boqez.data.remote.services.WSService
import com.lmuro.boqez.domain.model.ActiveGesture
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.domain.model.LocalPlayer
import com.lmuro.boqez.domain.model.OpponentPlayer
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
                    bottomCard = start.bottomCard,
                    roomCode = args.gameId
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
            is GameEvent.OnPlayCard -> playCard(event.card)
            is GameEvent.OnSwapCards -> TODO()
        }
    }

    private fun observeMessages() {
        viewModelScope.launch {
            wsService.messages.collect { message ->
                when (message.type) {
                    WebSocketMessageType.GESTURE -> {
                        val data =
                            Json.decodeFromJsonElement<SocketGestureResponse>(message.payload)
                        showGesture(data.userId, data.gesture)
                    }

                    WebSocketMessageType.PLAY_CARD -> {
                        val data =
                            Json.decodeFromJsonElement<SocketPlayCardResponse>(message.payload)
                        onCardPlayed(data)
                    }

                    WebSocketMessageType.FINISH_TRICK -> {
                        val data =
                            Json.decodeFromJsonElement<SocketPlayCardResponse>(message.payload)
                        onTrickFinished(data)
                    }

                    WebSocketMessageType.FINISH_ROUND -> {
                        val data =
                            Json.decodeFromJsonElement<SocketPlayCardResponse>(message.payload)
                        onRoundFinished(data)
                    }

                    WebSocketMessageType.FINISH_GAME -> {
                        val data =
                            Json.decodeFromJsonElement<SocketPlayCardResponse>(message.payload)
                        onGameFinished(data)
                    }

                    else -> Napier.v("Unhandled socket message.")
                }
            }
        }
    }

    private fun onGameFinished(data: SocketPlayCardResponse) {
        state.update {
            it.copy(
                tableCards = it.tableCards + (data.userId to data.card),
                scores = it.scores + (data.scores?.mapValues { (teamId, points) ->
                    (it.scores[teamId] ?: 0) + points
                } ?: emptyMap())
            )
        }
        //TODO show results of game finished, add option to play again
    }

    private fun onRoundFinished(data: SocketPlayCardResponse) {
        state.update {
            it.copy(
                tableCards = it.tableCards + (data.userId to data.card),
                currentPlayerId = data.nextPlayerId.orEmpty(),
                scores = it.scores + (data.scores?.mapValues { (teamId, points) ->
                    (it.scores[teamId] ?: 0) + points
                } ?: emptyMap()))
        }
        viewModelScope.launch {
            delay(1500)
            state.update {
                it.copy(
                    tableCards = emptyMap()
                )
            }
        }
    }

    private fun onTrickFinished(data: SocketPlayCardResponse) {
        state.update {
            it.copy(
                tableCards = it.tableCards + (data.userId to data.card),
                currentPlayerId = data.nextPlayerId.orEmpty()
            )
        }
        viewModelScope.launch {
            delay(1500)
            state.update {
                it.copy(
                    tableCards = emptyMap()
                )
            }
        }
    }

    private fun onCardPlayed(data: SocketPlayCardResponse) {
        state.update { it.copy(
            tableCards = it.tableCards + (data.userId to data.card),
            currentPlayerId = data.nextPlayerId.orEmpty(),
            hand = if (data.userId == it.userId) {
                it.hand - data.card
            } else {
                it.hand
            },
            teams = if (data.userId != it.userId) {
                it.teams.map { team ->
                    team.copy(
                        players = team.players.map { player ->
                            if (player.playerId == data.userId && player is OpponentPlayer) {
                                player.copy(handSize = player.handSize - 1)
                            } else player
                        }
                    )
                }
            } else {
                it.teams
            }
        )}
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
        if(state.value.currentPlayerId != state.value.userId) return
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

    private fun playCard(card: Card) {
        if(state.value.currentPlayerId != state.value.userId) return
        viewModelScope.launch {
            repository.playCard(
                gameId = state.value.roomCode,
                card = card
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