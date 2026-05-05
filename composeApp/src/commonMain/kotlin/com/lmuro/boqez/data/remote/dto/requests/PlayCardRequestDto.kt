package com.lmuro.boqez.data.remote.dto.requests

import com.lmuro.boqez.domain.model.Card
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayCardRequestDto(
    @SerialName("card") val card : Card,
    @SerialName("game_id") val gameId : String
)
