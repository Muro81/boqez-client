package com.lmuro.boqez.presentation.login

import com.lmuro.boqez.presentation.base.BaseState

data class LoginState(
    val email : String = "",
    val password : String = "",
    val emailError : String = "",
    val passwordError : String = "",
    override val isLoading: Boolean = false
) : BaseState(isLoading)
