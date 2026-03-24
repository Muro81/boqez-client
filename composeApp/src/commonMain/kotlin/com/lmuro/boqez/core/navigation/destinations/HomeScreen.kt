package com.lmuro.boqez.core.navigation.destinations

import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.presentation.home.HomeScreen

fun NavGraphBuilder.homeComposable(
    showSnackBar : (String) -> Unit
){
    composable<Screen.HomeScreen> {
        HomeScreen(
            showSnackBar = showSnackBar
        )
    }
}