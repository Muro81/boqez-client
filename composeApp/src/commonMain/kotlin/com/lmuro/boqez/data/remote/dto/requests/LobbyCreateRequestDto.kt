package com.lmuro.boqez.data.remote.dto.requests

import com.lmuro.boqez.core.utils.GameType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LobbyCreateRequestDto(
    @SerialName("is_public") val isPublic: Boolean,
    @SerialName("game_type") val gameType: GameType?
)
