package com.lmuro.boqez.data.remote.dto.socket

import com.lmuro.boqez.core.utils.WebSocketMessageType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class WebSocketMessage(
    val type: WebSocketMessageType,
    val payload: JsonElement
)