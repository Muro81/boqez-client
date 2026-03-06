package com.lmuro.boqez.data.remote.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequestDto(
    @SerialName("token") val token: String,
    @SerialName("device") val device: String
)
