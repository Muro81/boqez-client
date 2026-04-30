package com.lmuro.boqez.core.navigation

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.Suit
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.domain.model.LobbyUser
import com.lmuro.boqez.domain.model.Team
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
    data class LobbyScreen(
        val lobbyId: String,
        val gameType: GameType? = null,
        val players : List<LobbyUser> = emptyList(),
        val userId : String,
        val ownerId : String,
        val username : String
    ) : Screen

    @Serializable
    data class GameScreen(
        val gameId : String,
        val gameType: GameType,
        val teams : List<Team>,
        val currentPlayerId : String,
        val deck : List<Card>,
        val discardPile : List<Card>,
        val trumpSuit : Suit?
    ) : Screen
}