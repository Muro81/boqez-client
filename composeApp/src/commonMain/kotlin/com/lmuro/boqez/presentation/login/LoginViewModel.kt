package com.lmuro.boqez.presentation.login

import androidx.lifecycle.viewModelScope
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.error_field_required
import boqez.composeapp.generated.resources.error_invalid_email
import boqez.composeapp.generated.resources.error_password_min_lenght
import com.lmuro.boqez.core.networking.onError
import com.lmuro.boqez.core.networking.onSuccess
import com.lmuro.boqez.core.utils.isValidEmail
import com.lmuro.boqez.data.local.DataStoreApi
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.ACCESS_TOKEN
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.DEVICE_ID
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.DEVICE_NAME
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.REFRESH_TOKEN
import com.lmuro.boqez.domain.repository.BoqezRepository
import com.lmuro.boqez.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

class LoginViewModel(
    private val repository: BoqezRepository,
    private val dataStoreApi: DataStoreApi
) : BaseViewModel<LoginState, LoginEvent>() {
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
        viewModelScope.launch {
            val emailError = when {
                state.value.email.isBlank() -> getString(Res.string.error_field_required)
                !state.value.email.isValidEmail() -> getString(Res.string.error_invalid_email)
                else -> ""
            }

            val passwordError = when {
                state.value.password.isBlank() -> getString(Res.string.error_field_required)
                state.value.password.length !in 8..100 -> getString(Res.string.error_password_min_lenght)
                else -> ""
            }

            state.update { it.copy(emailError = emailError, passwordError = passwordError) }

            if (emailError.isNotEmpty() || passwordError.isNotEmpty()) return@launch
            login()
        }
    }

    private fun login() {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            val deviceName = dataStoreApi.read(DEVICE_NAME).orEmpty()
            val deviceId = dataStoreApi.read(DEVICE_ID).orEmpty()
            repository.login(
                email = state.value.email,
                password = state.value.password,
                device = deviceName + "_" + deviceId
            ).onSuccess { result ->
                dataStoreApi.update(ACCESS_TOKEN, result.accessToken)
                dataStoreApi.update(REFRESH_TOKEN, result.refreshToken)
                _snackBarChannel.send("Success on login. $ACCESS_TOKEN")
            }.onError { error, message ->
                _snackBarChannel.send(message ?: error.toString())
            }
            state.update { it.copy(isLoading = false) }
        }
    }
}