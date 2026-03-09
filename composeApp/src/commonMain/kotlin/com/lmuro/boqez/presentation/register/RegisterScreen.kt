package com.lmuro.boqez.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.confirm_password
import boqez.composeapp.generated.resources.email
import boqez.composeapp.generated.resources.hint_confirm_password
import boqez.composeapp.generated.resources.hint_email
import boqez.composeapp.generated.resources.hint_password
import boqez.composeapp.generated.resources.hint_username
import boqez.composeapp.generated.resources.password
import boqez.composeapp.generated.resources.sign_up
import boqez.composeapp.generated.resources.username
import com.lmuro.boqez.core.utils.ObserveWithLifecycle
import com.lmuro.boqez.presentation.base.BaseContentView
import com.lmuro.boqez.presentation.components.BackArrowHeader
import com.lmuro.boqez.presentation.components.BoqezTextField
import com.lmuro.boqez.presentation.components.PrimaryButton
import com.lmuro.boqez.theme.BoqezThemeProvider
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = koinViewModel(),
    showSnackBar: (String) -> Unit
) {
    val state by viewModel.stateFlow.collectAsState()
    viewModel.snackBarChanel.ObserveWithLifecycle {
        showSnackBar(it)
    }

    BaseContentView(
        state = state
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BoqezThemeProvider.colors.white)
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            BackArrowHeader {
                viewModel.onEvent(RegisterEvent.OnBackClick)
            }
            BoqezTextField(
                value = state.email,
                placeHolder = stringResource(Res.string.hint_email),
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.OnEmailChanged(it))
                },
                label = stringResource(Res.string.email),
                errorMessage = state.emailError,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
            )

            BoqezTextField(
                value = state.username,
                placeHolder = stringResource(Res.string.hint_username),
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.OnUsernameChanged(it))
                },
                label = stringResource(Res.string.username),
                errorMessage = state.usernameError,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
            )


            BoqezTextField(
                value = state.password,
                placeHolder = stringResource(Res.string.hint_password),
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.OnPasswordChanged(it))
                },
                label = stringResource(Res.string.password),
                errorMessage = state.passwordError,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                isPassword = true
            )

            BoqezTextField(
                value = state.confirmPassword,
                placeHolder = stringResource(Res.string.hint_confirm_password),
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.OnPasswordChanged(it))
                },
                label = stringResource(Res.string.confirm_password),
                errorMessage = state.confirmPasswordError,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                isPassword = true
            )
            Spacer(Modifier.weight(1f))

            PrimaryButton(
                onClick = {
                    viewModel.onEvent(RegisterEvent.OnRegisterClick)
                }
            ) {
                Text(
                    text = stringResource(Res.string.sign_up),
                    style = BoqezThemeProvider.typography.interMedium16,
                )
            }
            Spacer(Modifier.weight(1f))
        }
    }

}