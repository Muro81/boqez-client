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

@Composable
fun OpponentHand(
    count: Int,
    cardWidth: Dp = 24.dp,
    tiltDegrees: Float = 9f,
    vertical: Boolean = false,
    flipLift: Boolean = false,
) {
    if (count == 0) return
    val cardHeight = cardWidth * 1.4f

    Box(
        modifier = Modifier.then(
            if (vertical)
                Modifier
                    .width(cardHeight + 8.dp)
                    .height(cardWidth + cardWidth * 0.55f * (count - 1))
            else
                Modifier
                    .width(cardWidth + cardWidth * 0.55f * (count - 1))
                    .height(cardHeight + 8.dp)
        ),
        contentAlignment = Alignment.Center,
    ) {
        repeat(count) { i ->
            val mid = (count - 1) / 2f
            val offset = i - mid
            val rot = offset * tiltDegrees
            val lift = (kotlin.math.abs(offset) * 2).dp

            Box(
                modifier = Modifier.then(
                    if (vertical)
                        Modifier
                            .offset(y = (offset * cardWidth.value * 0.55f).dp)
                            .rotate(if (flipLift) 90f - rot else 90f + rot)
                            .offset(x = if (flipLift) -lift else lift)
                    else
                        Modifier
                            .offset(x = (offset * cardWidth.value * 0.55f).dp)
                            .rotate(rot)
                            .offset(y = lift)
                )
            ) {
                CardBack(width = cardWidth)
            }
        }
    }
}