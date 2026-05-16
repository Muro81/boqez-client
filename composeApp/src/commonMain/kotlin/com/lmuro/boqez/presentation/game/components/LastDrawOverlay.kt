package com.lmuro.boqez.presentation.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.core.utils.noRippleClickable
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun LastDrawOverlay(
    teammateUsername: String,
    cards: List<Card>,
    teamPoints: Int,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BoqezThemeProvider.colors.inkWarm.copy(alpha = 0.85f))
            .noRippleClickable(onDismiss),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Your team has $teamPoints points",
                style = BoqezThemeProvider.typography.cinzelBold14,
                color = BoqezThemeProvider.colors.goldLight
            )
            Text(
                text = "$teammateUsername's hand",
                style = BoqezThemeProvider.typography.garamondItalic12,
                color = BoqezThemeProvider.colors.white.copy(alpha = 0.7f)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                cards.forEach { card ->
                    CardFace(card = card)
                }
            }
            Text(
                text = "Tap to dismiss",
                style = BoqezThemeProvider.typography.garamondItalic12,
                color = BoqezThemeProvider.colors.white.copy(alpha = 0.4f)
            )
        }
    }
}