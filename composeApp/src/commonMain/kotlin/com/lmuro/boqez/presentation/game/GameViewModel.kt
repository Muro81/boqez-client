package com.lmuro.boqez.presentation.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.core.networking.onError
import com.lmuro.boqez.core.networking.onSuccess
import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.Gesture
import com.lmuro.boqez.core.utils.WebSocketMessageType
import com.lmuro.boqez.data.local.DataStoreApi
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.ACTIVE_GAME_ID
import com.lmuro.boqez.data.local.GameStateCache
import com.lmuro.boqez.data.remote.dto.socket.SocketCallCardsResponse
import com.lmuro.boqez.data.remote.dto.socket.SocketGameStartResponse
import com.lmuro.boqez.data.remote.dto.socket.SocketGestureResponse
import com.lmuro.boqez.data.remote.dto.socket.SocketPlayCardResponse
import com.lmuro.boqez.data.remote.dto.socket.SocketShowHandsResponse
import com.lmuro.boqez.data.remote.dto.socket.SocketUserResponse
import com.lmuro.boqez.data.remote.mappers.toTeam
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
import kotlin.time.Duration.Companion.seconds

class GameViewModel(
    gameStateCache: GameStateCache,
    private val repository: BoqezRepository,
    private val wsService: WSService,
    private val navigator: Navigator,
    private val dataStoreApi: DataStoreApi,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<GameState, GameEvent>() {

    override val initialState: GameState = GameState()

    private val gestureJobs = mutableMapOf<String, Job>()
    private var calledCardsJob: Job? = null
    private val disconnectCountdownJobs = mutableMapOf<String, Job>()

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
                        ?.hand
                        ?.sortedWith(Card.tresetaComparator) ?: emptyList(),
                    gameType = start.gameType,
                    teams = start.teams,
                    userId = args.userId,
                    currentPlayerId = start.currentPlayerId,
                    deck = start.deck,
                    discardPile = start.discardPile,
                    trumpSuit = start.trumpSuit,
                    bottomCard = start.bottomCard,
                    roomCode = args.gameId,
                    dealerPeekCard = start.dealerPeekCard
                )
            }
        }
        viewModelScope.launch {
            dataStoreApi.update(ACTIVE_GAME_ID, args.gameId)
        }
        if (gameStartState?.dealerPeekCard != null) showDealerPeekCard()
        observeMessages()
    }

    override fun onEvent(event: GameEvent) {
        when (event) {
            GameEvent.OnCallCards -> callPoints()
            is GameEvent.OnGesture -> sendGesture(event.gesture)
            GameEvent.OnLeaveGame -> state.update { it.copy(showLeaveConfirm = true) }
            GameEvent.OnLeaveGameDismiss -> state.update { it.copy(showLeaveConfirm = false) }
            GameEvent.OnLeaveGameConfirm -> leaveGame()
            is GameEvent.OnPlayCard -> playCard(event.card)
            is GameEvent.OnSwapCards -> TODO()
            GameEvent.OnReady -> sendReady()
            GameEvent.OnDeckClick -> {
                if (state.value.dealerPeekCard != null) showDealerPeekCard()
            }

            GameEvent.OnDismissTeammateHand -> state.update { it.copy(showTeammateHand = false) }
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

                    WebSocketMessageType.SHOW_HANDS -> {
                        val data =
                            Json.decodeFromJsonElement<SocketShowHandsResponse>(message.payload)
                        state.update {
                            it.copy(
                                revealedTeammateHand = Pair(data.userId, data.cards),
                                teammateCardPoints = data.teamPoints,
                                showTeammateHand = true
                            )
                        }
                    }

                    WebSocketMessageType.FINISH_ROUND -> {
                        val data =
                            Json.decodeFromJsonElement<SocketPlayCardResponse>(message.payload)
                        onRoundFinished(data, false)
                    }

                    WebSocketMessageType.FINISH_GAME -> {
                        val data =
                            Json.decodeFromJsonElement<SocketPlayCardResponse>(message.payload)
                        onGameFinished(data)
                    }

                    WebSocketMessageType.ROUND_DRAW -> {
                        val data =
                            Json.decodeFromJsonElement<SocketPlayCardResponse>(message.payload)
                        onRoundFinished(data, true)
                    }

                    WebSocketMessageType.GAME_DELETED -> {
                        viewModelScope.launch {
                            dataStoreApi.delete(ACTIVE_GAME_ID)
                            wsService.disconnect()
                            navigator.navigateTo(
                                destination = Screen.HomeScreen
                            ) {
                                popUpTo<Screen.ROOT>()
                            }
                        }
                    }

                    WebSocketMessageType.CALL_POINTS -> {
                        val data =
                            Json.decodeFromJsonElement<SocketCallCardsResponse>(message.payload)
                        calledCardsJob?.cancel()
                        state.update {
                            it.copy(
                                calledCards = Pair(data.userId, data.cards),
                                hasCalledThisRound = if (data.userId == it.userId) true else it.hasCalledThisRound
                            )
                        }
                        calledCardsJob = viewModelScope.launch {
                            delay(3000)
                            state.update { it.copy(calledCards = null) }
                        }
                    }

                    WebSocketMessageType.START_ROUND -> {
                        val data =
                            Json.decodeFromJsonElement<SocketGameStartResponse>(message.payload)
                        val newTeams = data.teams.map {
                            it.toTeam(
                                state.value.userId,
                                data.hand,
                                gameType = data.gameType
                            )
                        }
                        state.update {
                            it.copy(
                                hand = newTeams
                                    .flatMap { team -> team.players }
                                    .filterIsInstance<LocalPlayer>()
                                    .firstOrNull()
                                    ?.hand
                                    ?.sortedWith(Card.tresetaComparator) ?: emptyList(),
                                teams = newTeams,
                                currentPlayerId = data.currentPlayerId,
                                deck = data.deck,
                                discardPile = data.discardPile,
                                trumpSuit = data.trumpSuit,
                                bottomCard = data.bottomCard,
                                tableCards = emptyMap(),
                                trickNumber = 0,
                                hasCalledThisRound = false,
                                showRoundEndOverlay = false,
                                isRoundDraw = false,
                                activeGestures = emptyMap(),
                                calledCards = null,
                                isTrickFinishing = false,
                                isReady = false,
                                dealerPeekCard = data.dealerPeekCard,
                                revealedTeammateHand = null,
                                teammateCardPoints = null,
                            )
                        }
                        if (data.dealerPeekCard != null) showDealerPeekCard()
                    }

                    WebSocketMessageType.PLAYER_DISCONNECTED -> {
                        val data = Json.decodeFromJsonElement<SocketUserResponse>(message.payload)
                        onPlayerDisconnected(data.userId)
                    }

                    WebSocketMessageType.PLAYER_RECONNECTED -> {
                        val data = Json.decodeFromJsonElement<SocketUserResponse>(message.payload)
                        onPlayerReconnected(data.userId)
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
                scores = data.scores ?: it.scores,
                hand = if (data.userId == it.userId) {
                    (it.hand - data.card).sortedWith(Card.tresetaComparator)
                } else {
                    it.hand
                }
            )
        }
        viewModelScope.launch {
            dataStoreApi.delete(ACTIVE_GAME_ID)
        }
        //TODO show results of game finished, add option to play again
    }

    private fun onRoundFinished(data: SocketPlayCardResponse, draw: Boolean) {
        state.update {
            it.copy(
                tableCards = it.tableCards + (data.userId to data.card),
                currentPlayerId = data.nextPlayerId.orEmpty(),
                scores = data.scores ?: it.scores,
                trickNumber = 0,
                hasCalledThisRound = false,
                hand = if (data.userId == it.userId) {
                    (it.hand - data.card).sortedWith(Card.tresetaComparator)
                } else {
                    it.hand
                }
            )
        }
        viewModelScope.launch {
            delay(1500)
            state.update {
                it.copy(
                    tableCards = emptyMap(),
                    showRoundEndOverlay = true,
                    isRoundDraw = draw
                )
            }
        }
    }

    private fun onTrickFinished(data: SocketPlayCardResponse) {
        //TODO visual for who won the trick and card drawing
        val isBriskula = state.value.gameType == GameType.BRISKULA
        val deckNotEmpty = state.value.deck.isNotEmpty()

        state.update {
            it.copy(
                tableCards = it.tableCards + (data.userId to data.card),
                currentPlayerId = data.nextPlayerId.orEmpty(),
                deck = if (isBriskula) it.deck.dropLast(4) else it.deck,
                hand = run {
                    val handAfterPlay =
                        if (data.userId == it.userId) it.hand - data.card else it.hand
                    if (data.drawnCard != null) (handAfterPlay + data.drawnCard).sortedWith(Card.tresetaComparator)
                    else handAfterPlay.sortedWith(Card.tresetaComparator)
                },
                teams = it.teams.map { team ->
                    team.copy(
                        players = team.players.map { player ->
                            if (player.playerId == data.userId && player is OpponentPlayer) {
                                val delta = if (isBriskula && deckNotEmpty) 0 else -1
                                player.copy(handSize = player.handSize + delta)
                            } else player
                        }
                    )
                },
                trickNumber = it.trickNumber + 1,
                isTrickFinishing = true
            )
        }

        clearBottomCardIfNeeded()

        viewModelScope.launch {
            delay(1500)
            state.update {
                it.copy(
                    tableCards = emptyMap(),
                    isTrickFinishing = false
                )
            }
        }
    }

    private fun onCardPlayed(data: SocketPlayCardResponse) {
        state.update {
            it.copy(
                tableCards = it.tableCards + (data.userId to data.card),
                currentPlayerId = data.nextPlayerId.orEmpty(),
                hand = if (data.userId == it.userId) {
                    (it.hand - data.card).sortedWith(Card.tresetaComparator)
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
            )
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
        if (!state.value.canGesture) return
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
        if (!state.value.isMyTurn) return
        if (state.value.isTrickFinishing) return
        if (state.value.gameType == GameType.TRESETA) {
            val leadSuit = state.value.tableCards.values.firstOrNull()?.suit
            if (leadSuit != null && card.suit != leadSuit) {
                val hasSuit = state.value.hand.any { it.suit == leadSuit }
                if (hasSuit) {
                    viewModelScope.launch {
                        _snackBarChannel.send("You must play a $leadSuit card.")
                    }
                    return
                }
            }
        }
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

    private fun callPoints() {
        viewModelScope.launch {
            val combos = state.value.availableCallCombinations
            if (combos.isEmpty()) return@launch
            // call all available combinations
            combos.forEach { combo ->
                repository.callPoints(
                    gameId = state.value.roomCode,
                    cards = combo
                ).onError { _, message ->
                    _snackBarChannel.send(message ?: "Failed to call cards.")
                }
            }
        }
    }

    private fun sendReady() {
        if (state.value.isReady) return
        viewModelScope.launch {
            repository.sendReady(gameId = state.value.roomCode)
                .onSuccess {
                    state.update {
                        it.copy(
                            isReady = true
                        )
                    }
                }.onError { networkError, message ->
                    val decide = message ?: networkError.toString()
                    _snackBarChannel.send(decide)

                }
        }
    }

    private fun onPlayerDisconnected(userId: String) {
        disconnectCountdownJobs[userId]?.cancel()
        state.update {
            it.copy(disconnectedPlayers = it.disconnectedPlayers + (userId to 60))
        }
        disconnectCountdownJobs[userId] = viewModelScope.launch {
            for (secondsLeft in 59 downTo 0) {
                delay(1.seconds)
                state.update {
                    it.copy(disconnectedPlayers = it.disconnectedPlayers + (userId to secondsLeft))
                }
            }
            disconnectCountdownJobs.remove(userId)
        }
    }

    private fun onPlayerReconnected(userId: String) {
        disconnectCountdownJobs[userId]?.cancel()
        disconnectCountdownJobs.remove(userId)
        state.update {
            it.copy(disconnectedPlayers = it.disconnectedPlayers - userId)
        }
    }

    private fun leaveGame() {
        viewModelScope.launch {
            repository.leaveGame(gameId = state.value.roomCode)
                .onError { networkError, message ->
                    val decide = message ?: networkError.toString()
                    _snackBarChannel.send(decide)
                }
            // Navigation handled by GAME_DELETED message the server broadcasts
            state.update { it.copy(showLeaveConfirm = false) }
        }
    }

    private fun showDealerPeekCard() {
        state.update { it.copy(showDealerPeek = true) }
        viewModelScope.launch {
            delay(3000)
            state.update { it.copy(showDealerPeek = false) }
        }
    }

    private fun clearBottomCardIfNeeded() {
        state.update {
            it.copy(
                bottomCard = if (it.deck.isEmpty()) null else it.bottomCard
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        gestureJobs.values.forEach { it.cancel() }
        gestureJobs.clear()
        disconnectCountdownJobs.values.forEach { it.cancel() }
        disconnectCountdownJobs.clear()
    }

}