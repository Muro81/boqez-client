package com.lmuro.boqez.data.remote.mappers

import com.lmuro.boqez.data.remote.dto.socket.SocketTeam
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.domain.model.Team

fun SocketTeam.toTeam(currentUserId: String, hand: List<Card>) = Team(
    teamId = teamId,
    players = players.map { it.toPlayer(currentUserId,hand) }
)