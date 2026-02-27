package com.lmuro.boqez.presentation.login

import com.lmuro.boqez.core.utils.isValidEmail
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
            LoginEvent.OnLoginClick -> validateInputs()
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

    private fun validateInputs() {

        state.update { it.copy(emailError = "", passwordError = "") }

        val emailError = when {
            state.value.email.isBlank() -> "Field required."
            !state.value.email.isValidEmail() -> "Invalid email format."
            else -> ""
        }

        val passwordError = when {
            state.value.password.isBlank() -> "Field required."
            state.value.password.length !in 8..100 -> "Password must be at least 8 characters."
            else -> ""
        }

        state.update { it.copy(emailError = emailError, passwordError = passwordError) }

        if (emailError.isNotEmpty() || passwordError.isNotEmpty()) return
        login()
    }

    private fun login() {
        println("ALL GOOD")
    }
}