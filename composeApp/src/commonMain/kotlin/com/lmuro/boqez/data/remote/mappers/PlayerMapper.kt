package com.lmuro.boqez.data.remote.mappers

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.data.remote.dto.socket.SocketUser
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.domain.model.LocalPlayer
import com.lmuro.boqez.domain.model.OpponentPlayer

fun SocketUser.toPlayer(currentUserId: String, hand: List<Card>,gameType : GameType) = if (userId == currentUserId) {
    LocalPlayer(playerId = userId, username = username, seatIndex = seatIndex, hand = hand)
} else {
    val handSize = when(gameType){
        GameType.BRISKULA -> 3
        GameType.TRESETA -> 10
        GameType.TERCULJA -> 12
    }
    OpponentPlayer(playerId = userId, username = username, seatIndex = seatIndex, handSize = handSize)
}