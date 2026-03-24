package com.lmuro.boqez.data.remote.mappers

import com.lmuro.boqez.data.remote.dto.response.LobbyJoinResponseDto
import com.lmuro.boqez.domain.model.LobbyJoin

fun LobbyJoinResponseDto.toJoinLobby(): LobbyJoin {
    return LobbyJoin(
        lobbyId = lobbyId,
        gameType = gameType,
        players = players.map { it.toLobbyUser() }
    )
}