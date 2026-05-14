package com.lmuro.boqez.presentation.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.domain.model.Team
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun RoundEndOverlay(
    teams: List<Team>,
    scores: Map<Int, Int>,
    isRoundDraw: Boolean,
    isReady: Boolean,
    onReady: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BoqezThemeProvider.colors.inkWarm.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(BoqezThemeProvider.colors.parchment)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (isRoundDraw) "DRAW" else "ROUND OVER",
                style = BoqezThemeProvider.typography.cinzelBold14,
                color = BoqezThemeProvider.colors.crimsonBase,
            )

            if (isRoundDraw) {
                Text(
                    text = "The round ended in a draw.\nScores are unchanged.",
                    style = BoqezThemeProvider.typography.garamondItalic12,
                    color = BoqezThemeProvider.colors.inkWarmDim,
                    textAlign = TextAlign.Center
                )
            }

            // Score rows
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BoqezThemeProvider.colors.goldDark, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                teams.forEachIndexed { index, team ->
                    val score = scores[team.teamId] ?: 0
                    val bg = if (index == 0) BoqezThemeProvider.colors.crimsonBase else BoqezThemeProvider.colors.feltLight
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(bg)
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "T${team.teamId}  $score",
                            style = BoqezThemeProvider.typography.cinzelSemiBold12,
                            color = BoqezThemeProvider.colors.goldLight
                        )
                    }
                }
            }

            Button(
                onClick = onReady,
                enabled = !isReady,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BoqezThemeProvider.colors.crimsonBase,
                    disabledContainerColor = BoqezThemeProvider.colors.inkWarmDim
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isReady) "Waiting..." else "Ready",
                    style = BoqezThemeProvider.typography.cinzelBold14,
                    color = BoqezThemeProvider.colors.goldLight,
                )
            }
        }
    }
}