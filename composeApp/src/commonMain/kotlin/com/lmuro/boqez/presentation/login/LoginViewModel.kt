package com.lmuro.boqez.presentation.login

import androidx.lifecycle.viewModelScope
import com.lmuro.boqez.core.locale.getDefaultLocale
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.core.networking.onError
import com.lmuro.boqez.core.networking.onSuccess
import com.lmuro.boqez.core.utils.Const
import com.lmuro.boqez.core.utils.validateEmail
import com.lmuro.boqez.core.utils.validatePasswordMin
import com.lmuro.boqez.data.local.DataStoreApi
import com.lmuro.boqez.data.local.DataStorePreferenceKeys
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.ACCESS_TOKEN
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.DEVICE_ID
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.DEVICE_NAME
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.REFRESH_TOKEN
import com.lmuro.boqez.domain.repository.BoqezRepository
import com.lmuro.boqez.presentation.base.BaseViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: BoqezRepository,
    private val dataStoreApi: DataStoreApi,
    private val navigator: Navigator
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

            LoginEvent.OnSignUpClick -> {
                viewModelScope.launch {
                    navigator.navigateTo(
                        destination = Screen.RegisterScreen
                    )
                }
            }
        }
    }

    init {
        setupLanguages()
    }

    private fun setupLanguages() {
        dataStoreApi.readAsFlow(
            DataStorePreferenceKeys.USER_PREFERRED_LANGUAGE
        )
            .onStart { emit(getDefaultLocale()) }
            .onEach { savedTag ->
                val langList = Const.languages
                val selectedLanguage = langList.find {
                    it.tag == savedTag
                } ?: langList.first() // fallback to first if not found

                state.update {
                    it.copy(
                        selectedLanguage = selectedLanguage
                    )
                }
                Napier.v("LANG IS $selectedLanguage")
            }
            .launchIn(
                viewModelScope
            )
    }

    private fun validateInputs() {
        viewModelScope.launch {
            val emailError = state.value.email.validateEmail()

            val passwordError = state.value.password.validatePasswordMin()

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
                Napier.v("Tokens ${result.accessToken} ${result.refreshToken}")
                dataStoreApi.update(ACCESS_TOKEN, result.accessToken)
                dataStoreApi.update(REFRESH_TOKEN, result.refreshToken)
                navigator.navigateTo(
                    destination = Screen.HomeScreen
                ){
                    popUpTo(Screen.ROOT)
                }
            }.onError { error, message ->
                val decide = message ?: error.toString()
                state.update {
                    it.copy(
                        passwordError = decide
                    )
                }
            }
            state.update { it.copy(isLoading = false) }
        }
    }
}