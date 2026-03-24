package com.lmuro.boqez.presentation.home

import com.lmuro.boqez.presentation.base.BaseEvent

sealed class HomeEvent : BaseEvent {
    data object OnPlayClick : HomeEvent()
    data object OnSettingsClick : HomeEvent()
    data object OnProfileClick : HomeEvent()
    data object OnAboutUsClick : HomeEvent()
}