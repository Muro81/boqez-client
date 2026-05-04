package com.lmuro.boqez.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.core.utils.ObserveWithLifecycle
import com.lmuro.boqez.presentation.base.BaseContentView
import com.lmuro.boqez.presentation.game.components.GameTable
import com.lmuro.boqez.presentation.game.components.GameTopBar
import com.lmuro.boqez.theme.BoqezThemeProvider
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = koinViewModel(),
    showSnackBar: (String) -> Unit,
) {
    viewModel.snackBarChanel.ObserveWithLifecycle { showSnackBar(it) }
    val state by viewModel.stateFlow.collectAsState()

    BaseContentView(state = state) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BoqezThemeProvider.colors.parchment)
                .padding(10.dp)
        ) {
            GameTopBar(
                gameType = state.gameType,
                onMenuClick = { viewModel.onEvent(GameEvent.OnLeaveGame) }
            )
//            if (state.gameType != GameType.TERCULJA) {
//                state.teammate?.let { teammate ->
//                    teammate as OpponentPlayer
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically,
//                    ) {
//                        Avatar(gesture = state.activeGestures[teammate.playerId])
//                        Spacer(modifier = Modifier.weight(1f))
//                        OpponentHand(
//                            count = teammate.handSize,
//                            cardWidth = 30.dp,
//                            tiltDegrees = 10f
//                        )
//                    }
//                }
//            }
            GameTable(
                tableCards = state.tableCards,
                positionedPlayers = state.positionedPlayers,
                deckCount = state.deck.size,
                bottomCard = state.bottomCard,
                activeGestures = state.activeGestures,
            )
        }
    }
}