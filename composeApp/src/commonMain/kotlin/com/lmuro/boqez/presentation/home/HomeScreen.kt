package com.lmuro.boqez.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.app_name
import boqez.composeapp.generated.resources.play
import boqez.composeapp.generated.resources.settings
import com.lmuro.boqez.core.utils.ObserveWithLifecycle
import com.lmuro.boqez.presentation.base.BaseContentView
import com.lmuro.boqez.presentation.components.PrimaryButton
import com.lmuro.boqez.presentation.components.SecondaryButton
import com.lmuro.boqez.presentation.components.TitleBlock
import com.lmuro.boqez.presentation.home.components.LobbyPickerDialog
import com.lmuro.boqez.theme.BoqezThemeProvider
import org.jetbrains.compose.resources.stringResource
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




        Column(
            modifier = Modifier
                .background(BoqezThemeProvider.colors.parchment)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            TitleBlock(
                modifier = Modifier.weight(1f),
                appName = stringResource(Res.string.app_name)
            )

            PrimaryButton(
                onClick = {
                    viewModel.onEvent(HomeEvent.OnPlayClick)
                }
            ) {
                Text(
                    text = stringResource(Res.string.play),
                    style = BoqezThemeProvider.typography.cinzelBold16
                )
            }

            SecondaryButton(
                onClick = {
                    viewModel.onEvent(HomeEvent.OnSettingsClick)
                }
            ) {
                Text(
                    text = stringResource(Res.string.settings),
                    style = BoqezThemeProvider.typography.cinzelBold16
                )
            }

//            PrimaryButton(
//                onClick = {
//                    viewModel.onEvent(HomeEvent.OnAboutUsClick)
//                }
//            ) {
//                Text(
//                    text = "ABOUT US"
//                )
//            }
            Spacer(Modifier.weight(1f))
        }
        IconButton(
            onClick = {
                viewModel.onEvent(HomeEvent.OnProfileClick)
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset((-10).dp,10.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = BoqezThemeProvider.colors.feltDark,
                contentColor = BoqezThemeProvider.colors.goldLight
            )
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = BoqezThemeProvider.colors.goldLight
            )
        }

        if (state.showRejoinDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BoqezThemeProvider.colors.inkWarm.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(BoqezThemeProvider.colors.parchment)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "REJOIN GAME",
                        style = BoqezThemeProvider.typography.cinzelBold14,
                        color = BoqezThemeProvider.colors.crimsonBase,
                    )
                    Text(
                        text = "You have an active game in progress. Would you like to rejoin?",
                        style = BoqezThemeProvider.typography.garamondItalic12,
                        color = BoqezThemeProvider.colors.inkWarmDim,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.onEvent(HomeEvent.OnDismissRejoin) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Abandon",
                                style = BoqezThemeProvider.typography.cinzelBold14,
                                color = BoqezThemeProvider.colors.inkWarm
                            )
                        }
                        PrimaryButton(
                            onClick = { viewModel.onEvent(HomeEvent.OnRejoinGame) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Rejoin",
                                style = BoqezThemeProvider.typography.cinzelBold14,
                                color = BoqezThemeProvider.colors.goldLight
                            )
                        }
                    }
                }
            }
        }
    }
}