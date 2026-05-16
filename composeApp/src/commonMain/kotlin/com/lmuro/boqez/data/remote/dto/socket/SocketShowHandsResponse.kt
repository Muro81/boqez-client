package com.lmuro.boqez.data.remote.dto.socket

import com.lmuro.boqez.domain.model.Card
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketShowHandsResponse(
    @SerialName("user_id") val userId: String,
    @SerialName("cards") val cards: List<Card>,
    @SerialName("teamPoints") val teamPoints: Int
)