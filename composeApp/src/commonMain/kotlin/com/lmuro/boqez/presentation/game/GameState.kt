package com.lmuro.boqez.presentation.game

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.Suit
import com.lmuro.boqez.core.utils.TablePosition
import com.lmuro.boqez.domain.model.ActiveGesture
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.domain.model.PositionedPlayer
import com.lmuro.boqez.domain.model.Team
import com.lmuro.boqez.presentation.base.BaseState

data class GameState(
    val hand: List<Card> = emptyList(),
    val gameType: GameType? = null,
    val teams: List<Team> = emptyList(),
    val userId: String = "",
    val currentPlayerId: String = "",
    val deck: List<Card> = emptyList(),
    val discardPile: List<Card> = emptyList(),
    val trumpSuit: Suit? = null,
    val tableCards: Map<String, Card> = emptyMap(),
    val roomCode: String = "",
    val activeGestures: Map<String, ActiveGesture> = emptyMap(),
    val bottomCard : Card? = null,
    val scores : Map<Int,Int> = emptyMap(),
    override val isLoading: Boolean = false
) : BaseState(isLoading) {

    val positionedPlayers: List<PositionedPlayer>
        get() {
            val mySeat = teams.flatMap { it.players }
                .firstOrNull { it.playerId == userId }
                ?.seatIndex ?: return emptyList()

            return teams.flatMap { it.players }
                .filter { it.playerId != userId }
                .mapNotNull { player ->
                    val relative = ((player.seatIndex - mySeat) + 4) % 4
                    val position = when (relative) {
                        2 -> TablePosition.TOP
                        1 -> TablePosition.LEFT
                        3 -> TablePosition.RIGHT
                        else -> null
                    }
                    position?.let { PositionedPlayer(player, it) }
                }
        }

    val currentPlayerUsername: String
        get() = teams.flatMap { it.players }
            .firstOrNull { it.playerId == currentPlayerId }
            ?.username ?: ""

    val isMyTurn: Boolean
        get() = currentPlayerId == userId
}
