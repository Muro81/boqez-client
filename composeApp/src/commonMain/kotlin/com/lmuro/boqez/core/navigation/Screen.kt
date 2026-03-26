package com.lmuro.boqez.core.navigation

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.domain.model.LobbyUser
import kotlinx.serialization.Serializable


sealed interface Screen {

    @Serializable
    data object ROOT : Screen

    @Serializable
    data object LoginScreen : Screen

    @Serializable
    data object RegisterScreen : Screen

    @Serializable
    data object HomeScreen : Screen

    @Serializable
    data object LobbyCreateScreen : Screen

    @Serializable
    data class LobbyScreen(
        val lobbyId: String,
        val gameType: GameType? = null,
        val players : List<LobbyUser> = emptyList(),
        val userId : String,
        val ownerId : String
    ) : Screen
}