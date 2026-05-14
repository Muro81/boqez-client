package com.lmuro.boqez.presentation.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun DisconnectedBadge(secondsLeft: Int) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(BoqezThemeProvider.colors.crimsonBase.copy(alpha = 0.9f))
            .padding(horizontal = 4.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${secondsLeft}s",
            color = BoqezThemeProvider.colors.goldLight,
            style = BoqezThemeProvider.typography.cinzelSemiBold12
        )
    }
}