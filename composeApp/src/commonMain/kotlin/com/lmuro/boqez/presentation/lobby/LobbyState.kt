package com.lmuro.boqez.presentation.lobby

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.domain.model.LobbyUser
import com.lmuro.boqez.presentation.base.BaseState

data class LobbyState(
    override val isLoading: Boolean = false,
    val players : List<LobbyUser> =emptyList(),
    val ownerId : String = "",
    val userId : String = "",
    val lobbyId : String = "",
    val playerToKickId : String = "",
    val gameType : GameType? = null
) : BaseState(isLoading)
