package com.lmuro.boqez.core.navigation.utils

import androidx.navigation.NavOptionsBuilder
import com.lmuro.boqez.core.navigation.Screen
import kotlinx.coroutines.flow.Flow

interface Navigator {
    val navigationFlow : Flow<NavType>

    suspend fun navigateTo(
        destination : Screen,
        navOptions : NavOptionsBuilder.() -> Unit = {}
    )

    suspend fun navigateUp()
}