package com.lmuro.boqez.data.remote.dto.socket

import com.lmuro.boqez.core.utils.Gesture
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketGestureResponse(
    @SerialName("gesture") val gesture : Gesture,
    @SerialName("user_id") val userId : String
)
