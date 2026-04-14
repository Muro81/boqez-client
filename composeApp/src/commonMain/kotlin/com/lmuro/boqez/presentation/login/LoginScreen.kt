package com.lmuro.boqez.presentation.login

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
import androidx.compose.ui.text.style.TextDecoration
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
import com.lmuro.boqez.core.utils.Const
import com.lmuro.boqez.core.utils.ObserveWithLifecycle
import com.lmuro.boqez.core.utils.noRippleClickable
import com.lmuro.boqez.presentation.base.BaseContentView
import com.lmuro.boqez.presentation.components.BoqezTextField
import com.lmuro.boqez.presentation.components.LanguageDropdown
import com.lmuro.boqez.presentation.components.PrimaryButton
import com.lmuro.boqez.presentation.components.TitleBlock
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

    BaseContentView(state = state) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BoqezThemeProvider.colors.parchment)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ── Top bar: language dropdown ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp, bottom = 10.dp)
            ) {
                state.selectedLanguage?.let { selectedLanguage ->
                    LanguageDropdown(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        languages = Const.languages,
                        selectedLanguage = selectedLanguage,
                        onLanguageSelected = { newLang ->
                            switchLanguage(newLang.tag)
                        }
                    )
                }
            }

            // ── Title block ──
            TitleBlock(
                modifier = Modifier.weight(1f),
                appName = stringResource(Res.string.app_name)
            )

            // ── Form ──
            BoqezTextField(
                value = state.email,
                label = stringResource(Res.string.email),
                placeHolder = stringResource(Res.string.hint_email),
                errorMessage = state.emailError,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                onValueChange = {
                    viewModel.onEvent(LoginEvent.OnEmailChanged(it))
                }
            )

            Spacer(Modifier.height(16.dp))

            BoqezTextField(
                value = state.password,
                label = stringResource(Res.string.password),
                placeHolder = stringResource(Res.string.hint_password),
                errorMessage = state.passwordError,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                onValueChange = {
                    viewModel.onEvent(LoginEvent.OnPasswordChanged(it))
                },
                isPassword = true
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = stringResource(Res.string.forgot_password),
                color = BoqezThemeProvider.colors.goldDark,
                style = BoqezThemeProvider.typography.garamondItalic14,
                modifier = Modifier
                    .align(Alignment.Start)
                    .noRippleClickable {
                        viewModel.onEvent(LoginEvent.OnForgotPasswordClick)
                    },
                textDecoration = TextDecoration.Underline
            )

            // ── Actions ──
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
                ) {
                    Text(
                        text = stringResource(Res.string.login),
                        style = BoqezThemeProvider.typography.cinzelBold14,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = BoqezThemeProvider.colors.goldBase.copy(alpha = 0.35f)
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        text = stringResource(Res.string.or),
                        style = BoqezThemeProvider.typography.garamondItalic12,
                        color = BoqezThemeProvider.colors.inkWarmDim
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = BoqezThemeProvider.colors.goldBase.copy(alpha = 0.35f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(Res.string.sign_up),
                    color = BoqezThemeProvider.colors.feltDark,
                    style = BoqezThemeProvider.typography.cinzelSemiBold14,
                    modifier = Modifier.noRippleClickable {
                        viewModel.onEvent(LoginEvent.OnSignUpClick)
                    },
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}