package com.lmuro.boqez.domain.model

import com.lmuro.boqez.core.utils.GameType

data class LobbyJoin(
    val lobbyId: String,
    val gameType: GameType?,
    val players : List<LobbyUser>
)
