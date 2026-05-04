package com.lmuro.boqez.presentation.game.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lmuro.boqez.core.utils.TablePosition
import com.lmuro.boqez.domain.model.ActiveGesture
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.domain.model.OpponentPlayer
import com.lmuro.boqez.domain.model.PositionedPlayer
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun GameTable(
    tableCards: Map<String, Card>,
    positionedPlayers: List<PositionedPlayer>,
    deckCount: Int,
    bottomCard: Card?,
    activeGestures: Map<String, ActiveGesture>,
    modifier: Modifier = Modifier
) {
    val topPlayer = positionedPlayers.find { it.position == TablePosition.TOP }
    val leftPlayer = positionedPlayers.find { it.position == TablePosition.LEFT }
    val rightPlayer = positionedPlayers.find { it.position == TablePosition.RIGHT }
    val dashedCircleColor = BoqezThemeProvider.colors.white.copy(alpha = 0.1f)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.85f)
            .clip(RoundedCornerShape(16.dp))
            .background(BoqezThemeProvider.colors.feltDark)
    ) {

        // --- Dashed circle in center ---
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = dashedCircleColor,
                radius = size.minDimension * 0.3f,
                style = Stroke(
                    width = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f))
                )
            )
        }

        // --- Deck + trump card (top left) ---
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                bottomCard?.let {
                    CardFace(
                        card = it,
                        modifier = Modifier
                            .rotate(90f)
                            .offset(x = 18.dp, y = 8.dp)
                    )
                }
                CardBack(width = 32.dp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$deckCount LEFT",
                fontSize = 10.sp,
                color = BoqezThemeProvider.colors.goldLight.copy(alpha = 0.8f),
                fontStyle = FontStyle.Italic
            )
        }

        // --- Top player (teammate) ---
        topPlayer?.let { positioned ->
            val player = positioned.player as OpponentPlayer
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Avatar(
                    gesture = activeGestures[positioned.player.playerId],
                    borderColor = BoqezThemeProvider.colors.feltLight
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = positioned.player.username,
                    fontSize = 10.sp,
                    color = BoqezThemeProvider.colors.white.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                OpponentHand(count = player.handSize)
            }
        }

        // --- Left player ---
        leftPlayer?.let { positioned ->
            val player = positioned.player as OpponentPlayer
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Avatar(
                    gesture = activeGestures[positioned.player.playerId],
                    borderColor = BoqezThemeProvider.colors.crimsonBase
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = positioned.player.username,
                    fontSize = 10.sp,
                    color = BoqezThemeProvider.colors.white.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                OpponentHand(
                    count = player.handSize,
                    vertical = true,
                    flipLift = false
                )
            }
        }

        // --- Right player ---
        rightPlayer?.let { positioned ->
            val player = positioned.player as OpponentPlayer
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Avatar(
                    gesture = activeGestures[positioned.player.playerId],
                    borderColor = BoqezThemeProvider.colors.crimsonBase
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = positioned.player.username,
                    fontSize = 10.sp,
                    color = BoqezThemeProvider.colors.white.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                OpponentHand(
                    count = player.handSize,
                    vertical = true,
                    flipLift = true
                )
            }
        }

        // --- Table cards in center ---
        Box(modifier = Modifier.align(Alignment.Center)) {
            tableCards.entries.forEach { (playerId, card) ->
                val rotation = remember(playerId) { (-15..15).random().toFloat() }
                val offsetX = remember(playerId) { (-20..20).random().toFloat() }
                val offsetY = remember(playerId) { (-20..20).random().toFloat() }

                CardFace(
                    card = card,
                    modifier = Modifier
                        .offset(x = offsetX.dp, y = offsetY.dp)
                        .rotate(rotation)
                )
                Text(
                    text = positionedPlayers
                        .find { it.player.playerId == playerId }
                        ?.player?.username ?: "YOU",
                    fontSize = 9.sp,
                    color = BoqezThemeProvider.colors.white.copy(alpha = 0.7f),
                    modifier = Modifier.offset(x = offsetX.dp, y = (offsetY + 52).dp)
                )
            }
        }

        // --- "on the table" label ---
        Text(
            text = "on the table",
            fontSize = 10.sp,
            fontStyle = FontStyle.Italic,
            color = BoqezThemeProvider.colors.goldLight.copy(alpha = 0.4f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}