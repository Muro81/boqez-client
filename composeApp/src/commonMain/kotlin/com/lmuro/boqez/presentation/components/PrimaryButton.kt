package com.lmuro.boqez.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    containerColor: Color = BoqezThemeProvider.colors.crimsonBase,
    contentColor: Color = BoqezThemeProvider.colors.goldLight,
    shape: Shape = RoundedCornerShape(8.dp),
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    buttonContent: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = if (isEnabled) containerColor else BoqezThemeProvider.colors.skyBase,
                contentColor = if (isEnabled) contentColor else BoqezThemeProvider.colors.inkLighter
            ),
        shape = shape,
        enabled = isEnabled,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .then(
                if(isEnabled) Modifier.dropShadow(
                    shape = shape,
                    shadow = Shadow(
                        color = BoqezThemeProvider.colors.crimsonBase.copy(alpha = 0.4f),
                        radius = 18.dp,
                        spread = 0.dp,
                        offset = DpOffset(x = 0.dp, y = 4.dp)
                    )
                ) else Modifier
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            buttonContent()
        }
    }
}