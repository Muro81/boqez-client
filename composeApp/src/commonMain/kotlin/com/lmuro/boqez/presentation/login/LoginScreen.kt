package com.lmuro.boqez.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.app_name
import boqez.composeapp.generated.resources.email
import boqez.composeapp.generated.resources.forgot_password
import boqez.composeapp.generated.resources.hint_email
import boqez.composeapp.generated.resources.hint_password
import boqez.composeapp.generated.resources.login
import boqez.composeapp.generated.resources.or
import boqez.composeapp.generated.resources.password
import boqez.composeapp.generated.resources.sign_up
import com.lmuro.boqez.core.utils.ObserveWithLifecycle
import com.lmuro.boqez.core.utils.noRippleClickable
import com.lmuro.boqez.presentation.base.BaseContentView
import com.lmuro.boqez.presentation.components.BoqezTextField
import com.lmuro.boqez.presentation.components.PrimaryButton
import com.lmuro.boqez.theme.BoqezThemeProvider
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    showSnackBar: (String) -> Unit,
    switchLanguage: (String) -> Unit
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
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.app_name),
                    style = BoqezThemeProvider.typography.interBold32,
                    color = BoqezThemeProvider.colors.primaryBase
                )
            }
            BoqezTextField(
                value = state.email,
                label = stringResource(Res.string.email),
                placeHolder = stringResource(Res.string.hint_email),
                errorMessage = state.emailError,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                onValueChange = {
                    viewModel.onEvent(LoginEvent.OnEmailChanged(it))
                }
            )
            Spacer(Modifier.height(20.dp))
            BoqezTextField(
                value = state.password,
                label = stringResource(Res.string.password),
                placeHolder = stringResource(Res.string.hint_password),
                errorMessage = state.passwordError,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                onValueChange = {
                    viewModel.onEvent(LoginEvent.OnPasswordChanged(it))
                },
                isPassword = true
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(Res.string.forgot_password),
                color = BoqezThemeProvider.colors.inkBase,
                style = BoqezThemeProvider.typography.interRegular14,
                modifier = Modifier.noRippleClickable {
                    viewModel.onEvent(LoginEvent.OnForgotPasswordClick)
                }
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryButton(
                    onClick = {
                        viewModel.onEvent(LoginEvent.OnLoginClick)
                    },
                    isEnabled = true
                ) {
                    Text(
                        text = stringResource(Res.string.login),
                        style = BoqezThemeProvider.typography.interMedium16,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(Res.string.or),
                    color = BoqezThemeProvider.colors.inkBase,
                    style = BoqezThemeProvider.typography.interMedium14
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(Res.string.sign_up),
                    color = BoqezThemeProvider.colors.primaryBase,
                    style = BoqezThemeProvider.typography.interMedium16,
                    modifier = Modifier.noRippleClickable {
                        viewModel.onEvent(LoginEvent.OnSignUpClick)
                    }
                )
            }
        }
    }
}