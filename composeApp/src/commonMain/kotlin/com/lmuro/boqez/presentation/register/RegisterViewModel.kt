package com.lmuro.boqez.presentation.register

import androidx.lifecycle.viewModelScope
import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.core.networking.onError
import com.lmuro.boqez.core.networking.onSuccess
import com.lmuro.boqez.core.utils.validateConfirmPassword
import com.lmuro.boqez.core.utils.validateEmail
import com.lmuro.boqez.core.utils.validateField
import com.lmuro.boqez.core.utils.validatePassword
import com.lmuro.boqez.core.utils.validateUsername
import com.lmuro.boqez.data.local.DataStoreApi
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.ACCESS_TOKEN
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.DEVICE_ID
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.DEVICE_NAME
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.REFRESH_TOKEN
import com.lmuro.boqez.domain.repository.BoqezRepository
import com.lmuro.boqez.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: BoqezRepository,
    private val dataStoreApi: DataStoreApi,
    private val navigator: Navigator
) : BaseViewModel<RegisterState, RegisterEvent>() {
    override val initialState: RegisterState = RegisterState()

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.OnBackClick -> {
                viewModelScope.launch {
                    navigator.navigateUp()
                }
            }

            is RegisterEvent.OnConfirmPasswordChanged -> {
                state.update { it.copy(confirmPassword = event.confirmPassword) }
            }

            is RegisterEvent.OnEmailChanged -> {
                state.update { it.copy(email = event.email) }
            }

            is RegisterEvent.OnPasswordChanged -> {
                state.update { it.copy(password = event.password) }
            }

            RegisterEvent.OnRegisterClick -> validateFields()
            is RegisterEvent.OnUsernameChanged -> {
                state.update { it.copy(username = event.username) }
            }
        }
    }

    private fun validateFields() {
        viewModelScope.launch {
            val emailError = state.value.email.validateEmail()

            val usernameError = state.value.username.validateUsername()

            val passwordError = state.value.password.validatePassword()
            val confirmPasswordError =
                state.value.confirmPassword.validateConfirmPassword(state.value.password)

            state.update {
                it.copy(
                    emailError = emailError,
                    usernameError = usernameError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError
                )
            }
            if (emailError.isNotEmpty() || usernameError.isNotEmpty() || passwordError.isNotEmpty() || confirmPasswordError.isNotEmpty()) return@launch
            register()
        }

    }

    private fun register() {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            val deviceName = dataStoreApi.read(DEVICE_NAME).orEmpty()
            val deviceId = dataStoreApi.read(DEVICE_ID).orEmpty()
            repository.register(
                username = state.value.username,
                email = state.value.email,
                password = state.value.password,
                confirmPassword = state.value.confirmPassword,
                device = deviceName + "_" + deviceId
            ).onSuccess { result ->
                dataStoreApi.update(ACCESS_TOKEN, result.accessToken)
                dataStoreApi.update(REFRESH_TOKEN, result.refreshToken)
                _snackBarChannel.send("Success on register. ${result.accessToken}")
            }.onError { error, message ->
                val decide = message ?: error.toString()
                state.update {
                    it.copy(
                        confirmPasswordError = decide
                    )
                }
            }
            state.update { it.copy(isLoading = false) }
        }
    }
}