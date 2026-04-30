package com.lmuro.boqez.data.remote.mappers

import com.lmuro.boqez.data.remote.dto.socket.SocketUser
import com.lmuro.boqez.domain.model.LocalPlayer
import com.lmuro.boqez.domain.model.OpponentPlayer

fun SocketUser.toPlayer(currentUserId: String) = if (userId == currentUserId) {
    LocalPlayer(playerId = userId, username = username, hand = emptyList())
} else {
    OpponentPlayer(playerId = userId, username = username, handSize = 0)
}