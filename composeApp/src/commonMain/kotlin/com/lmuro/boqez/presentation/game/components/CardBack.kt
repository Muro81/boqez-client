package com.lmuro.boqez.presentation.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.img_card_back
import org.jetbrains.compose.resources.painterResource

@Composable
fun CardBack(
    width: Dp
) {
    val height = width * 1.4f
    Image(
        painter = painterResource(Res.drawable.img_card_back),
        contentDescription = null,
        modifier = Modifier
            .width(width)
            .height(height)
    )
}