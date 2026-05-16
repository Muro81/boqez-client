package com.lmuro.boqez.presentation.game

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.Rank
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
    val bottomCard: Card? = null,
    val scores: Map<Int, Int> = emptyMap(),
    val trickNumber: Int = 0,
    val hasCalledThisRound: Boolean = false,
    val isTrickFinishing: Boolean = false,
    val calledCards: Pair<String, List<Card>>? = null,
    val showRoundEndOverlay: Boolean = false,
    val isRoundDraw: Boolean = false,
    val isReady : Boolean = false,
    val disconnectedPlayers: Map<String, Int> = emptyMap(),
    val showLeaveConfirm: Boolean = false,
    val dealerPeekCard : Card? = null,
    val showDealerPeek: Boolean = false,
    val revealedTeammateHand: Pair<String, List<Card>>? = null,
    val teammateCardPoints: Int? = null,
    val showTeammateHand : Boolean = false,
    override val isLoading: Boolean = false,
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

    val availableCallCombinations: List<List<Card>>
        get() {
            if (trickNumber != 0) return emptyList()
            val combos = mutableListOf<List<Card>>()

            // Naples: A+2+3 of same suit
            Suit.entries.forEach { suit ->
                val naples = hand.filter {
                    it.suit == suit && it.rank in listOf(Rank.ACE, Rank.TWO, Rank.THREE)
                }
                if (naples.size == 3) combos.add(naples)
            }

            // Three/four of a kind for ACE, TWO, THREE only
            listOf(Rank.ACE, Rank.TWO, Rank.THREE).forEach { rank ->
                val matching = hand.filter { it.rank == rank }
                if (matching.size >= 3) combos.add(matching)
            }

            return combos
        }

    val canCallCards: Boolean
        get() = isMyTurn && trickNumber == 0 && !hasCalledThisRound &&
                gameType == GameType.TRESETA && availableCallCombinations.isNotEmpty()

    val canTresetaGesture = gameType == GameType.TRESETA &&
            isMyTurn &&
            tableCards.isEmpty()

    val canBriscolaGesture: Boolean
        get() = gameType == GameType.BRISKULA && trickNumber > 0

    val canGesture: Boolean
        get() = canTresetaGesture || canBriscolaGesture
}
