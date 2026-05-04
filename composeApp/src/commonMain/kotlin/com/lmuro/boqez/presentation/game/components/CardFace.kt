package com.lmuro.boqez.presentation.game.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lmuro.boqez.core.utils.toDrawableResource
import com.lmuro.boqez.domain.model.Card
import org.jetbrains.compose.resources.painterResource

@Composable
fun CardFace(
    card : Card,
    modifier : Modifier
){
    Image(
        painter = painterResource(card.toDrawableResource()),
        contentDescription = "${card.rank} of ${card.suit}",
        modifier = modifier
    )
}