package com.lmuro.boqez.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.confirm_password
import boqez.composeapp.generated.resources.email
import boqez.composeapp.generated.resources.hint_confirm_password
import boqez.composeapp.generated.resources.hint_email
import boqez.composeapp.generated.resources.hint_password
import boqez.composeapp.generated.resources.hint_username
import boqez.composeapp.generated.resources.or
import boqez.composeapp.generated.resources.password
import boqez.composeapp.generated.resources.register_desc
import boqez.composeapp.generated.resources.register_title
import boqez.composeapp.generated.resources.sign_up
import boqez.composeapp.generated.resources.username
import boqez.composeapp.generated.resources.your_details
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
                .background(BoqezThemeProvider.colors.parchment)
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            BackArrowHeader(
                title = stringResource(Res.string.register_title),
                desc = stringResource(Res.string.register_desc)
            ) {
                viewModel.onEvent(RegisterEvent.OnBackClick)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = BoqezThemeProvider.colors.goldBase.copy(alpha = 0.35f)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = stringResource(Res.string.your_details),
                    style = BoqezThemeProvider.typography.garamondItalic12,
                    color = BoqezThemeProvider.colors.inkWarmDim,
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = BoqezThemeProvider.colors.goldBase.copy(alpha = 0.35f)
                )
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

            HorizontalDivider(Modifier.fillMaxWidth(), color = BoqezThemeProvider.colors.goldBase)

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
                    viewModel.onEvent(RegisterEvent.OnConfirmPasswordChanged(it))
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
                    style = BoqezThemeProvider.typography.cinzelBold14,
                )
            }
            Spacer(Modifier.weight(1f))
        }
    }

}