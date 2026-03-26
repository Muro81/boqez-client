package com.lmuro.boqez.presentation.home

import com.lmuro.boqez.presentation.base.BaseState

data class HomeState(
    override val isLoading: Boolean = false,
    val username : String = "",
    val userId : String = "",
    val shouldShowLobbyDialog : Boolean = false,
    val lobbyCode : String = ""
) : BaseState(isLoading)
