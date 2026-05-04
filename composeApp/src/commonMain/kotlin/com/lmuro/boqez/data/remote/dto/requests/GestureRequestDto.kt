package com.lmuro.boqez.data.remote.dto.requests

import com.lmuro.boqez.core.utils.Gesture
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GestureRequestDto(
    @SerialName("gesture") val gesture : Gesture,
    @SerialName("game_id") val gameId : String
)
