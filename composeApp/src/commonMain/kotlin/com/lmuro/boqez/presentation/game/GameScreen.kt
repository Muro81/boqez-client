package com.lmuro.boqez.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.core.utils.ObserveWithLifecycle
import com.lmuro.boqez.presentation.base.BaseContentView
import com.lmuro.boqez.presentation.game.components.AvatarWithGesturePopup
import com.lmuro.boqez.presentation.game.components.CallCardsButton
import com.lmuro.boqez.presentation.game.components.GameTable
import com.lmuro.boqez.presentation.game.components.GameTopBar
import com.lmuro.boqez.presentation.game.components.PlayerHand
import com.lmuro.boqez.presentation.game.components.RoundEndOverlay
import com.lmuro.boqez.theme.BoqezThemeProvider
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = koinViewModel(),
    showSnackBar: (String) -> Unit,
) {
    viewModel.snackBarChanel.ObserveWithLifecycle { showSnackBar(it) }
    val state by viewModel.stateFlow.collectAsState()
    var showGesturePopup by remember { mutableStateOf(false) }

    LaunchedEffect(state.canTresetaGesture) {
        if (!state.canTresetaGesture) showGesturePopup = false
    }

    BaseContentView(state = state) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BoqezThemeProvider.colors.parchment)
                    .padding(10.dp)
            ) {
                GameTopBar(
                    gameType = state.gameType,
                    scores = state.scores,
                    teams = state.teams,
                    onMenuClick = { viewModel.onEvent(GameEvent.OnLeaveGame) }
                )

                GameTable(
                    tableCards = state.tableCards,
                    positionedPlayers = state.positionedPlayers,
                    deckCount = state.deck.size,
                    bottomCard = state.bottomCard,
                    activeGestures = state.activeGestures,
                    currentPlayerId = state.currentPlayerId,
                    gameType = state.gameType,
                    userId = state.userId,
                    calledCards = state.calledCards,
                    modifier = Modifier.weight(1f)
                )

                // Turn indicator
                Text(
                    text = if (state.isMyTurn) "Your turn"
                    else "${state.currentPlayerUsername}'s turn",
                    style = BoqezThemeProvider.typography.garamondItalic12,
                    color = if (state.isMyTurn)
                        BoqezThemeProvider.colors.crimsonBase
                    else
                        BoqezThemeProvider.colors.inkWarmDim,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )

                if (state.canCallCards) {
                    CallCardsButton(
                        onClick = { viewModel.onEvent(GameEvent.OnCallCards) }
                    )
                }

                // My player strip
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    AvatarWithGesturePopup(
                        gesture = state.activeGestures[state.userId],
                        borderColor = if (state.isMyTurn)
                            BoqezThemeProvider.colors.goldLight
                        else
                            BoqezThemeProvider.colors.feltLight,
                        pulse = state.isMyTurn,
                        showPopup = showGesturePopup && state.canTresetaGesture,
                        onAvatarClick = {
                            if (state.canTresetaGesture) showGesturePopup = !showGesturePopup
                        },
                        onGesture = { gesture ->
                            showGesturePopup = false
                            viewModel.onEvent(GameEvent.OnGesture(gesture))
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = state.teams.flatMap { it.players }
                            .firstOrNull { it.playerId == state.userId }
                            ?.username ?: "",
                        color = BoqezThemeProvider.colors.inkWarm,
                        style = BoqezThemeProvider.typography.cinzelBold14,
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // My hand
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    PlayerHand(
                        cards = state.hand,
                        gameType = state.gameType,
                        trumpSuit = state.trumpSuit,
                        trickNumber = state.trickNumber,
                        onCardClick = { viewModel.onEvent(GameEvent.OnPlayCard(it)) },
                        onGesture = { viewModel.onEvent(GameEvent.OnGesture(it)) }
                    )
                }

            }

            if (state.showRoundEndOverlay) {
                RoundEndOverlay(
                    teams = state.teams,
                    scores = state.scores,
                    isRoundDraw = state.isRoundDraw,
                    isReady = state.isReady,
                    onReady = { viewModel.onEvent(GameEvent.OnReady) }
                )
            }

        }
    }
}