package com.lmuro.boqez.data.remote.mappers

import com.lmuro.boqez.data.remote.dto.socket.SocketUser
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.domain.model.LocalPlayer
import com.lmuro.boqez.domain.model.OpponentPlayer

fun SocketUser.toPlayer(currentUserId: String, hand: List<Card>) = if (userId == currentUserId) {
    LocalPlayer(playerId = userId, username = username, hand = hand)
} else {
    OpponentPlayer(playerId = userId, username = username, handSize = 10)
}