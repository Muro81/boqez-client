package com.lmuro.boqez.data.remote.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangeTeamRequestDto(
    @SerialName("lobby_id") val lobbyId: String,
    @SerialName("team_id") val teamId: Int
)