package com.lmuro.boqez.core.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.core.navigation.navTypes.lobbyScreenTypeMap
import com.lmuro.boqez.presentation.lobby.LobbyScreen

fun NavGraphBuilder.lobbyComposable(
    showSnackBar: (String) -> Unit
) {
    composable<Screen.LobbyScreen>(
        typeMap = lobbyScreenTypeMap
    ) {
        LobbyScreen(showSnackBar = showSnackBar)
    }
}