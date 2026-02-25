package com.lmuro.boqez.presentation.login

import com.lmuro.boqez.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update

class LoginViewModel : BaseViewModel<LoginState, LoginEvent>() {
    override val initialState: LoginState = LoginState()
    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> {
                state.update {
                    it.copy(
                        email = event.email,
                        emailError = ""
                    )
                }
            }

            LoginEvent.OnForgotPasswordClick -> TODO()
            LoginEvent.OnLoginClick -> TODO()
            is LoginEvent.OnPasswordChanged -> {
                state.update {
                    it.copy(
                        password = event.password,
                        passwordError = ""
                    )
                }
            }

            LoginEvent.OnSignUpClick -> TODO()
        }
    }
}