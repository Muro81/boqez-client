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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.domain.model.Team
import com.lmuro.boqez.presentation.components.PrimaryButton
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun GameResultOverlay(
    teams: List<Team>,
    scores: Map<Int, Int>,
    winnerTeamId: Int?,
    isPlayAgainReady: Boolean,
    onPlayAgain: () -> Unit,
    onLeave: () -> Unit
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
                text = "GAME OVER",
                style = BoqezThemeProvider.typography.cinzelBold14,
                color = BoqezThemeProvider.colors.crimsonBase
            )

            winnerTeamId?.let {
                Text(
                    text = "Team $it wins!",
                    style = BoqezThemeProvider.typography.cinzelBold14,
                    color = BoqezThemeProvider.colors.goldLight
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BoqezThemeProvider.colors.goldDark, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                teams.forEachIndexed { index, team ->
                    val score = scores[team.teamId] ?: 0
                    val isWinner = team.teamId == winnerTeamId
                    val bg = if (isWinner) BoqezThemeProvider.colors.crimsonBase else BoqezThemeProvider.colors.feltLight
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

            PrimaryButton(
                onClick = onPlayAgain,
                isEnabled = !isPlayAgainReady,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isPlayAgainReady) "Waiting..." else "Play Again",
                    style = BoqezThemeProvider.typography.cinzelBold14,
                    color = BoqezThemeProvider.colors.goldLight
                )
            }

            OutlinedButton(
                onClick = onLeave,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Leave",
                    style = BoqezThemeProvider.typography.cinzelBold14,
                    color = BoqezThemeProvider.colors.inkWarm
                )
            }
        }
    }
}