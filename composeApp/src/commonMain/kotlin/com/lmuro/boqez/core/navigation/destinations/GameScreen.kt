package com.lmuro.boqez.core.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.presentation.game.GameScreen

fun NavGraphBuilder.gameComposable(
    showSnackBar: (String) -> Unit
) {
    composable<Screen.GameScreen> {
        GameScreen(
            showSnackBar = showSnackBar
        )
    }
}