package com.lmuro.boqez.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponseDto(
    @SerialName("user_id") val userId : String,
    @SerialName("username") val username : String
)
