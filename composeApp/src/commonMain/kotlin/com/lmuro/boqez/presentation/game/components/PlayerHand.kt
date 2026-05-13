package com.lmuro.boqez.presentation.game.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.Gesture
import com.lmuro.boqez.core.utils.Suit
import com.lmuro.boqez.core.utils.briscolaGestureFor
import com.lmuro.boqez.core.utils.noRippleClickable
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun PlayerHand(
    cards: List<Card>,
    gameType: GameType?,
    trumpSuit: Suit?,
    trickNumber: Int,
    cardWidth: Dp = 56.dp,
    tiltDegrees: Float = 6f,
    onCardClick: (Card) -> Unit,
    onGesture: (Gesture) -> Unit,
) {
    val count = cards.size
    val cardHeight = cardWidth * 1.4f
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = Modifier
            .width(cardWidth + cardWidth * 0.55f * (count - 1))
            .height(cardHeight + 24.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        cards.forEachIndexed { i, card ->
            val mid = (count - 1) / 2f
            val offset = i - mid
            val rot = offset * tiltDegrees
            val lift = (kotlin.math.abs(offset) * 2).dp
            val isSelected = selectedIndex == i
            val liftAmount by animateFloatAsState(
                targetValue = if (isSelected) -20f else 0f,
                animationSpec = tween(150),
                label = "lift_$i"
            )

            // Briscola: show gesture button only on trump cards, not on first trick
            val briscolaGesture = if (
                gameType == GameType.BRISKULA &&
                isSelected &&
                trickNumber > 0
            ) briscolaGestureFor(card, trumpSuit) else null

            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .offset(x = (offset * cardWidth.value * 0.55f).dp)
                    .rotate(rot)
                    .offset(y = lift + liftAmount.dp)
                    .zIndex(if (isSelected) 10f else i.toFloat())
                    .noRippleClickable {
                        if (isSelected) {
                            onCardClick(card)
                            selectedIndex = null
                        } else {
                            selectedIndex = i
                        }
                    }
            ) {
                CardFace(
                    card = card,
                    modifier = Modifier
                        .width(cardWidth)
                        .height(cardHeight)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = BoqezThemeProvider.colors.goldDark.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(12.dp)
                        )
                )

                // Gesture button floats above lifted card
                briscolaGesture?.let { gesture ->
                    CardGestureButton(
                        visible = isSelected,
                        gesture = gesture,
                        onClick = {
                            onGesture(gesture)
                            selectedIndex = null
                        },
                        modifier = Modifier
                            .offset(y = (-28).dp)
                            .zIndex(11f)
                    )
                }
            }
        }
    }
}