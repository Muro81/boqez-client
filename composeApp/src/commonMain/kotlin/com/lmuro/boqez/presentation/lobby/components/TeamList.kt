package com.lmuro.boqez.presentation.lobby.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.join_temp
import com.lmuro.boqez.core.utils.noRippleClickable
import com.lmuro.boqez.domain.model.LobbyUser
import com.lmuro.boqez.theme.BoqezThemeProvider
import org.jetbrains.compose.resources.stringResource

@Composable
fun TeamList(
    modifier: Modifier = Modifier,
    teamName: String,
    headerColor: Color,
    maxTeamSize: Int,
    players: List<LobbyUser>,
    currentUserId: String,
    ownerId: String,
    canJoinTeam: Boolean,
    onJoinTeam: () -> Unit,
    onKickPlayer: () -> Unit
) {
    val dashedBorderColor = BoqezThemeProvider.colors.goldDark

    Column(
        modifier = modifier
            .background(BoqezThemeProvider.colors.parchment, RoundedCornerShape(16.dp))
            .border(1.dp, BoqezThemeProvider.colors.goldDark, RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(headerColor, RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = teamName,
                style = BoqezThemeProvider.typography.cinzelBold24,
                color = BoqezThemeProvider.colors.goldLight
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            players.forEach { player ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(BoqezThemeProvider.colors.white, RoundedCornerShape(8.dp))
                        .border(1.dp, BoqezThemeProvider.colors.goldDark, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = player.username,
                        color = if (player.userId == currentUserId) BoqezThemeProvider.colors.goldLight else BoqezThemeProvider.colors.feltDarkest,
                        style = BoqezThemeProvider.typography.garamondItalic16
                    )
                    if (player.isReady) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(
                                    BoqezThemeProvider.colors.greenLightest,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(
                                        BoqezThemeProvider.colors.greenBase,
                                        CircleShape
                                    )
                            )
                        }
                    }
                }
            }
            if (canJoinTeam) {
                val remainingSlots = maxTeamSize - players.size
                repeat(remainingSlots) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .drawBehind {
                                val pathEffect =
                                    PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                                drawRoundRect(
                                    color = dashedBorderColor,
                                    style = Stroke(width = 1.dp.toPx(), pathEffect = pathEffect),
                                    cornerRadius = CornerRadius(8.dp.toPx())
                                )
                            }.noRippleClickable(onJoinTeam)
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = BoqezThemeProvider.colors.goldDark,
                            modifier = Modifier.drawBehind {
                                val pathEffect =
                                    PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                                drawCircle(
                                    color = dashedBorderColor,
                                    style = Stroke(width = 1.dp.toPx(), pathEffect = pathEffect),
                                )
                            }
                        )

                        Text(
                            text = stringResource(Res.string.join_temp, teamName),
                            color = BoqezThemeProvider.colors.goldDark,
                            style = BoqezThemeProvider.typography.garamondItalic16
                        )
                    }
                }
            }
        }
    }
}