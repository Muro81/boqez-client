package com.lmuro.boqez.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun TitleBlock(
    appName: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = appName,
            style = BoqezThemeProvider.typography.cinzelBlack40,
            color = BoqezThemeProvider.colors.crimsonBase,
            textAlign = TextAlign.Center
        )

    }
}