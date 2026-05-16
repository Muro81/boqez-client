package com.lmuro.boqez.data.remote.mappers

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.data.remote.dto.response.RejoinGameResponseDto
import com.lmuro.boqez.data.remote.dto.response.RejoinPlayerDto
import com.lmuro.boqez.data.remote.dto.response.RejoinTeamDto
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.domain.model.LocalPlayer
import com.lmuro.boqez.domain.model.OpponentPlayer
import com.lmuro.boqez.domain.model.Rejoin
import com.lmuro.boqez.domain.model.Team

fun RejoinGameResponseDto.toRejoin(currentUserId: String) = Rejoin(
    gameId = gameId,
    gameType = gameType,
    teams = teams.map { it.toTeam(currentUserId, hand, gameType) },
    currentPlayerId = currentPlayerId,
    deck = deck,
    discardPile = discardPile,
    trumpSuit = trumpSuit,
    bottomCard = bottomCard,
    hand = hand,
    dealerPeekCard = dealerPeekCard
)

fun RejoinTeamDto.toTeam(currentUserId: String, hand: List<Card>, gameType: GameType) = Team(
    teamId = teamId,
    players = players.map { it.toPlayer(currentUserId, hand, gameType) }
)

fun RejoinPlayerDto.toPlayer(currentUserId: String, hand: List<Card>, gameType: GameType) =
    if (userId == currentUserId) {
        LocalPlayer(playerId = userId, username = username, seatIndex = seatIndex, hand = hand)
    } else {
        OpponentPlayer(
            playerId = userId,
            username = username,
            seatIndex = seatIndex,
            handSize = if (gameType == GameType.BRISKULA) 3 else 10
        )
    }