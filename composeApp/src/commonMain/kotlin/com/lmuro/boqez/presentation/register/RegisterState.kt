package com.lmuro.boqez.presentation.register

import com.lmuro.boqez.presentation.base.BaseState

data class RegisterState(
    val username : String = "",
    val usernameError : String = "",
    val email : String = "",
    val emailError : String = "",
    val password : String = "",
    val passwordError : String = "",
    val confirmPassword : String = "",
    val confirmPasswordError : String = "",
    override val isLoading: Boolean = false
) : BaseState(isLoading)
