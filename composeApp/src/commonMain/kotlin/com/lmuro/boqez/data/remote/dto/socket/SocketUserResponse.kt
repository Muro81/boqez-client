package com.lmuro.boqez.data.remote.dto.socket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketUserResponse(
    @SerialName("user_id") val userId: String,
    val username: String
)
