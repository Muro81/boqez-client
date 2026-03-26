package com.lmuro.boqez.data.remote.dto.response

import com.lmuro.boqez.core.utils.GameType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LobbyJoinResponseDto(
    @SerialName("lobby_id") val lobbyId: String,
    @SerialName("game_type") val gameType: GameType?,
    val players : List<LobbyUserDto>,
    @SerialName("owner_id") val ownerId : String
)
