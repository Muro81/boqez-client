package com.lmuro.boqez.presentation.game

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.Suit
import com.lmuro.boqez.core.utils.TablePosition
import com.lmuro.boqez.domain.model.ActiveGesture
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.domain.model.Player
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
            val myTeam = teams.find { team -> team.players.any { it.playerId == userId } }
            val opponentTeam = teams.find { team -> team.players.none { it.playerId == userId } }

            val teammate = myTeam?.players?.find { it.playerId != userId }
            val opponents = opponentTeam?.players ?: emptyList()

            return buildList {
                teammate?.let { add(PositionedPlayer(it, TablePosition.TOP)) }
                opponents.getOrNull(0)?.let { add(PositionedPlayer(it, TablePosition.LEFT)) }
                opponents.getOrNull(1)?.let { add(PositionedPlayer(it, TablePosition.RIGHT)) }
            }
        }
}
