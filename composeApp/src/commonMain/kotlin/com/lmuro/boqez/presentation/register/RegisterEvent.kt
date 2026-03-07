package com.lmuro.boqez.presentation.register

import com.lmuro.boqez.presentation.base.BaseEvent

sealed class RegisterEvent : BaseEvent {
    data class OnEmailChanged(val email : String) : RegisterEvent()
    data class OnUsernameChanged(val username : String) : RegisterEvent()
    data class OnPasswordChanged(val password : String) : RegisterEvent()
    data class OnConfirmPasswordChanged(val confirmPassword : String) : RegisterEvent()
    data object OnBackClick : RegisterEvent()
    data object OnRegisterClick : RegisterEvent()
}