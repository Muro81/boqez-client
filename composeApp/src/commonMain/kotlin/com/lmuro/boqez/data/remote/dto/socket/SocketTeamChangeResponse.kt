package com.lmuro.boqez.data.remote.dto.socket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketTeamChangeResponse(
    @SerialName("user_id") val userId: String,
    val username: String,
    @SerialName("team_id") val teamId: Int
)

