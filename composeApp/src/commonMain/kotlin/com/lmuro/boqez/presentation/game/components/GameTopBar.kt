package com.lmuro.boqez.presentation.game.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun GameTopBar(
    gameType: GameType?,
    onMenuClick: () -> Unit,
) {
    val colors = BoqezThemeProvider.colors
    val typography = BoqezThemeProvider.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding( vertical = 10.dp),
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
    }
}
