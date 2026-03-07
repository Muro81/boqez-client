package com.lmuro.boqez.data.remote.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    @SerialName("email") val email: String,
    @SerialName("username") val username: String,
    @SerialName("password") val password: String,
    @SerialName("device") val device : String,
    @SerialName("confirm_password") val confirmPassword : String
)
