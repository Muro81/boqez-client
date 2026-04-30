package com.lmuro.boqez.presentation.lobby

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.domain.model.LobbyUser
import com.lmuro.boqez.presentation.base.BaseState

data class LobbyState(
    override val isLoading: Boolean = false,
    val players: List<LobbyUser> = emptyList(),
    val ownerId: String = "",
    val userId: String = "",
    val lobbyId: String = "",
    val playerToKickId: String = "",
    val gameType: GameType? = null
) : BaseState(isLoading) {

    val teamA = players.filter { it.teamId == 1 }
    val teamB = players.filter { it.teamId == 2 }
    val noTeam = players.filter { it.teamId == null }
    val myTeamId = players.find { it.userId == userId }?.teamId

    val isOwner: Boolean
        get() = ownerId == userId

    val canStartGame: Boolean
        get() {
            val allReady = players.isNotEmpty() && players.all { (it.isReady || it.userId == ownerId) }

            val hasEnoughPlayers = when (gameType) {
                GameType.BRISKULA,
                GameType.TRESETA -> teamA.size == 2 && teamB.size == 2

                GameType.TERCULJA -> players.size == 3

                null -> false
            }
            return allReady && hasEnoughPlayers
        }
}
