package com.lmuro.boqez.data.remote.dto.socket

import com.lmuro.boqez.domain.model.Card
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketPlayCardResponse(
    @SerialName("card") val card: Card,
    @SerialName("user_id") val userId : String,
    @SerialName("next_player_id") val nextPlayerId : String?,// send next player id on trick finished/unfinished
    @SerialName("scores") val scores : Map<Int,Int>? //send scores on round and game complete
)
