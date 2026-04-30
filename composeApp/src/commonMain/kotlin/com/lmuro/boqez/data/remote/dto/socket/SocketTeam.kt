package com.lmuro.boqez.data.remote.dto.socket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketTeam(
    @SerialName("team_id") val teamId : Int,
    @SerialName("players") val players : List<SocketUser>
)
