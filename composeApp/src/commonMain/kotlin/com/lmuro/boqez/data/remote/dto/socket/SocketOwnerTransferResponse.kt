package com.lmuro.boqez.data.remote.dto.socket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketOwnerTransferResponse(
    @SerialName("old_owner_id")
    val oldOwnerId: String,
    @SerialName("old_owner_username")
    val oldOwnerUsername: String,
    @SerialName("new_owner_id")
    val newOwnerId: String,
    @SerialName("new_owner_username")
    val newOwnerUsername: String
)
