package com.lmuro.boqez.core.navigation.utils

import androidx.navigation.NavOptionsBuilder
import com.lmuro.boqez.core.navigation.Screen

sealed class NavType {
    data object NavigateUp : NavType()

    data class NavigateToRoute(
        val destination : Screen,
        val navOptions : NavOptionsBuilder.() -> Unit = {}
    ) : NavType()
}