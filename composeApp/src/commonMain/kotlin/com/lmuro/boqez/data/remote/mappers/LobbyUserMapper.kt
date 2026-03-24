package com.lmuro.boqez.data.remote.mappers

import com.lmuro.boqez.data.remote.dto.response.LobbyUserDto
import com.lmuro.boqez.domain.model.LobbyUser

fun LobbyUserDto.toLobbyUser(): LobbyUser {
    return LobbyUser(
        userId = userId,
        username = username,
        isReady = isReady,
        teamId = teamId
    )
}