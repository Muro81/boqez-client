package com.lmuro.boqez.core.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.presentation.register.RegisterScreen

fun NavGraphBuilder.registerComposable(
    showSnackBar: (String) -> Unit
) {
    composable<Screen.RegisterScreen> {
        RegisterScreen(
            showSnackBar = showSnackBar
        )
    }
}