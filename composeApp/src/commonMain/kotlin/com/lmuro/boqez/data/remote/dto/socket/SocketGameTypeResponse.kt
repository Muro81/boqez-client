package com.lmuro.boqez.data.remote.dto.socket

import com.lmuro.boqez.core.utils.GameType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketGameTypeResponse(
    @SerialName("game_type") val gameType : GameType
)
