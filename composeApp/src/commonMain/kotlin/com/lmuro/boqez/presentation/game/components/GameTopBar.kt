package com.lmuro.boqez.presentation.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.domain.model.Team
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun GameTopBar(
    gameType: GameType?,
    scores: Map<Int, Int>,
    teams: List<Team>,
    onMenuClick: () -> Unit,
) {
    val colors = BoqezThemeProvider.colors
    val typography = BoqezThemeProvider.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier
                .size(36.dp)
                .border(1.dp, colors.parchmentDark, RoundedCornerShape(8.dp))
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = colors.inkWarm,
            )
        }

        Text(
            text = gameType?.gameName?.uppercase() ?: "",
            style = typography.cinzelBold14,
            color = colors.crimsonBase,
            textAlign = TextAlign.Center,
            letterSpacing = 2.sp,
        )

        // Score chip — one segment per team
        Row(
            modifier = Modifier
                .border(1.dp, colors.goldDark, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            teams.forEachIndexed { index, team ->
                val score = scores[team.teamId] ?: 0
                val bg = if (index == 0) colors.crimsonBase else colors.feltLight
                Box(
                    modifier = Modifier
                        .background(bg)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "T${team.teamId} $score",
                        style = typography.cinzelBold14,
                        color = colors.goldLight,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}
