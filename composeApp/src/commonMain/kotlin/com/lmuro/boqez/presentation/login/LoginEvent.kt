package com.lmuro.boqez.presentation.login

import com.lmuro.boqez.presentation.base.BaseEvent

sealed class LoginEvent : BaseEvent {
    data class OnEmailChanged(val email: String) : LoginEvent()
    data class OnPasswordChanged(val password: String) : LoginEvent()
    data object OnLoginClick : LoginEvent()
    data object OnForgotPasswordClick : LoginEvent()
    data object OnSignUpClick : LoginEvent()
}