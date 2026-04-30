package com.lmuro.boqez.data.remote.dto.socket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketUser(
    @SerialName("user_id") val userId : String,
    @SerialName("username") val username : String
)
