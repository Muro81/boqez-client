package com.lmuro.boqez.presentation.home

import com.lmuro.boqez.presentation.base.BaseEvent

sealed class HomeEvent : BaseEvent {
    data object OnPlayClick : HomeEvent()
    data object OnSettingsClick : HomeEvent()
    data object OnProfileClick : HomeEvent()
    data object OnAboutUsClick : HomeEvent()
    data class OnLobbyCodeChanged(val code : String) : HomeEvent()
    data object OnLobbyCreate : HomeEvent()
    data object OnLobbyJoin : HomeEvent()
    data object OnDissmissDialog : HomeEvent()
}