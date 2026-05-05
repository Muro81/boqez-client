package com.lmuro.boqez.presentation.game.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.core.utils.noRippleClickable
import com.lmuro.boqez.domain.model.Card

@Composable
fun PlayerHand(
    cards: List<Card>,
    cardWidth: Dp = 56.dp,
    tiltDegrees: Float = 6f,
    onCardClick: (Card) -> Unit
) {
    val count = cards.size
    val cardHeight = cardWidth * 1.4f

    Box(
        modifier = Modifier
            .width(cardWidth + cardWidth * 0.55f * (count - 1))
            .height(cardHeight + 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        cards.forEachIndexed { i, card ->
            val mid = (count - 1) / 2f
            val offset = i - mid
            val rot = offset * tiltDegrees
            val lift = (kotlin.math.abs(offset) * 2).dp

            Box(
                modifier = Modifier
                    .offset(x = (offset * cardWidth.value * 0.55f).dp)
                    .rotate(rot)
                    .offset(y = lift)
                    .noRippleClickable { onCardClick(card) }
            ) {
                CardFace(
                    card = card,
                    modifier = Modifier
                        .width(cardWidth)
                        .height(cardHeight)
                )
            }
        }
    }
}