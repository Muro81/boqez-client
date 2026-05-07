package com.lmuro.boqez.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LobbyUserDto(
    @SerialName("user_id") val userId: String,
    @SerialName("username") val username: String,
    @SerialName("is_ready") val isReady: Boolean,
    @SerialName("team_id") val teamId: Int?
)
