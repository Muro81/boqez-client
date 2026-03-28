package com.lmuro.boqez.data.remote.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReadyStatusRequestDto(
    @SerialName("is_ready") val isReady: Boolean,
    @SerialName("lobby_id") val lobbyId: String
)