package com.lmuro.boqez.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.core.utils.ObserveWithLifecycle
import com.lmuro.boqez.presentation.base.BaseContentView
import com.lmuro.boqez.presentation.components.PrimaryButton
import com.lmuro.boqez.presentation.home.components.LobbyPickerDialog
import com.lmuro.boqez.theme.BoqezThemeProvider
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    showSnackBar: (String) -> Unit
) {
    viewModel.snackBarChanel.ObserveWithLifecycle {
        showSnackBar(it)
    }

    val state by viewModel.stateFlow.collectAsState()

    BaseContentView(
        state = state
    ) {
        LobbyPickerDialog(
            shouldShow = state.shouldShowLobbyDialog,
            lobbyCode = state.lobbyCode,
            onLobbyCodeChanged = {
                viewModel.onEvent(HomeEvent.OnLobbyCodeChanged(it))
            },
            onLobbyCreate = {
                viewModel.onEvent(HomeEvent.OnLobbyCreate)
            },
            onLobbyJoin = {
                viewModel.onEvent(HomeEvent.OnLobbyJoin)
            },
            onDismissDialog = {
                viewModel.onEvent(HomeEvent.OnDissmissDialog)
            }
        )
        IconButton(
            onClick = {
                viewModel.onEvent(HomeEvent.OnProfileClick)
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = BoqezThemeProvider.colors.primaryDarkest
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(Modifier.weight(1f))
            PrimaryButton(
                onClick = {
                    viewModel.onEvent(HomeEvent.OnPlayClick)
                }
            ) {
                Text(
                    text = "PLAY"
                )
            }

            PrimaryButton(
                onClick = {
                    viewModel.onEvent(HomeEvent.OnSettingsClick)
                }
            ) {
                Text(
                    text = "SETTINGS"
                )
            }

            PrimaryButton(
                onClick = {
                    viewModel.onEvent(HomeEvent.OnAboutUsClick)
                }
            ) {
                Text(
                    text = "ABOUT US"
                )
            }
            Spacer(Modifier.weight(1f))
        }
    }
}