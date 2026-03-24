package com.lmuro.boqez.data.remote.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LobbyWrapperRequestDto(
    @SerialName("lobby_id") val lobbyId: String
)
