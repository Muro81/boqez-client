package com.lmuro.boqez.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun BoqezTheme(
    content: @Composable () -> Unit
) {
    val boqezTypography = provideBoqezTypography()
    CompositionLocalProvider(
        LocalBoqezTypography provides boqezTypography,
        LocalBoqezColors provides boqezColors,
        content = content
    )
}

object BoqezThemeProvider {
    val typography: BoqezTypography
        @Composable get() = LocalBoqezTypography.current
    val colors: BoqezColors
        @Composable get() = LocalBoqezColors.current
}