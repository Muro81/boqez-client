package com.lmuro.boqez.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.not_ready
import boqez.composeapp.generated.resources.ready
import com.lmuro.boqez.theme.BoqezThemeProvider
import org.jetbrains.compose.resources.stringResource

@Composable
fun ReadyButton(
    isReady: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp)

    val containerColor = if (isReady)
        Color.Transparent
    else
        BoqezThemeProvider.colors.crimsonBase

    val contentColor = if (isReady)
        BoqezThemeProvider.colors.greenBase
    else
        BoqezThemeProvider.colors.goldLight

    val borderModifier = if (isReady)
        Modifier.border(1.dp, BoqezThemeProvider.colors.greenBase, shape)
    else
        Modifier

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .then(borderModifier)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            if (isReady) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = if (isReady) stringResource(Res.string.ready) else stringResource(Res.string.not_ready),
                style = BoqezThemeProvider.typography.cinzelBold24
                    .copy(fontSize = androidx.compose.ui.unit.TextUnit(16f, androidx.compose.ui.unit.TextUnitType.Sp))
            )
        }
    }
}