package com.lmuro.boqez.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun BackArrowHeader(
    topPadding: Dp = 50.dp,
    title : String = "",
    desc : String = "",
    tint: Color = BoqezThemeProvider.colors.inkWarm,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = topPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .border(1.dp, BoqezThemeProvider.colors.parchmentDark, RoundedCornerShape(8.dp))
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = tint
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = title,
                color = BoqezThemeProvider.colors.crimsonBase,
                style = BoqezThemeProvider.typography.cinzelBold16
            )
            Text(
                text = desc,
                color = BoqezThemeProvider.colors.inkWarmDim,
                style = BoqezThemeProvider.typography.garamondItalic12
            )
        }
    }
}