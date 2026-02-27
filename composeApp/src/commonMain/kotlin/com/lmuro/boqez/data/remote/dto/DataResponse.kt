package com.lmuro.boqez.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataResponse<T>(
    @SerialName("data") val data : T,
    @SerialName("message") val message : String?
)
