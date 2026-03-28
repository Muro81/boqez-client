package com.lmuro.boqez.data.remote.dto.socket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketUserReadyResponse(
    @SerialName("user_id") val userId: String,
    @SerialName("is_ready") val isReady: Boolean
)