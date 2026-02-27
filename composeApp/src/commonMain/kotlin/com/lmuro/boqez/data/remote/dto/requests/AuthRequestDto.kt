package com.lmuro.boqez.data.remote.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestDto(
    @SerialName("email") val email : String,
    @SerialName("password") val password : String,
    @SerialName("device") val device : String
)
