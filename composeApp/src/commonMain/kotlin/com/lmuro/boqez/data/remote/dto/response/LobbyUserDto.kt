package com.lmuro.boqez.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LobbyUserDto(
    @SerialName("user_id") val userId: String,
    val username: String,
    val isReady: Boolean,
    val teamId: Int?
)
