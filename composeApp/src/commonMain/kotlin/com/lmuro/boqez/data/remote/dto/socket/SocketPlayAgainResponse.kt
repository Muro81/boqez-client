package com.lmuro.boqez.data.remote.dto.socket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketPlayAgainResponse(
    @SerialName("lobby_id") val lobbyId: String,
    @SerialName("owner_id") val ownerId : String
)