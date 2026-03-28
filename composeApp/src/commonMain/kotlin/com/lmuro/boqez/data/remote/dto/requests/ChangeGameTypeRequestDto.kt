package com.lmuro.boqez.data.remote.dto.requests

import com.lmuro.boqez.core.utils.GameType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ChangeGameTypeRequestDto(
    @SerialName("lobby_id") val lobbyId : String,
    @SerialName("game_type") val gameType: GameType
)