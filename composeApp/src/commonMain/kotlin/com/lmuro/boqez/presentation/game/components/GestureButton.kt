package com.lmuro.boqez.presentation.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun GestureButton(
    emoji: String,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(38.dp)
            .background(
                color = BoqezThemeProvider.colors.feltLight,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = BoqezThemeProvider.colors.goldBase,
                shape = CircleShape
            )
            .clickable { onClick() }
    ) {
        Text(
            text = emoji,
            fontSize = 18.sp
        )
    }
}