package com.lmuro.boqez.core.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.theme.BoqezThemeProvider

object CustomModifiers {
    val snackBarHost: @Composable SnackbarHostState.() -> Unit = {
        SnackbarHost(hostState = this) { snackBarData ->
            Snackbar(
                snackbarData = snackBarData,
                containerColor = BoqezThemeProvider.colors.white,
                contentColor = BoqezThemeProvider.colors.inkBase,
                actionColor = BoqezThemeProvider.colors.inkBase,
                shape = RoundedCornerShape(4.dp)
            )
        }
    }
}