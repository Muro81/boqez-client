package com.lmuro.boqez.presentation.lobby

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.unit.dp
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.room_temp
import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.ObserveWithLifecycle
import com.lmuro.boqez.core.utils.capitalize
import com.lmuro.boqez.core.utils.noRippleClickable
import com.lmuro.boqez.presentation.base.BaseContentView
import com.lmuro.boqez.presentation.components.PrimaryButton
import com.lmuro.boqez.presentation.lobby.components.GameTypeCard
import com.lmuro.boqez.theme.BoqezThemeProvider
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LobbyScreen(
    viewModel: LobbyViewModel = koinViewModel(),
    showSnackBar: (String) -> Unit
) {
    viewModel.snackBarChanel.ObserveWithLifecycle {
        showSnackBar(it)
    }
    val state by viewModel.stateFlow.collectAsState()


    BaseContentView(
        state = state
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BoqezThemeProvider.colors.parchment)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            //Game type and code section
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = state.gameType?.gameName?.capitalize() ?: "",
                    style = BoqezThemeProvider.typography.cinzelBold32,
                    color = BoqezThemeProvider.colors.crimsonBase
                )

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(
                            BoqezThemeProvider.colors.parchmentMid,
                            RoundedCornerShape(8.dp)
                        ).border(
                            1.dp,
                            BoqezThemeProvider.colors.parchmentDark,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .noRippleClickable{
                           //TODO add copy text to clipboard
                        }

                ) {
                    Text(
                        text = stringResource(Res.string.room_temp,state.lobbyId).uppercase(),
                        style = BoqezThemeProvider.typography.cinzelRegular14,
                        color = BoqezThemeProvider.colors.goldDark
                    )
                }

                IconButton(
                    onClick = {
                        viewModel.onEvent(LobbyEvent.OnLeaveGame)
                    },
                    modifier = Modifier.border(
                        1.dp,
                        BoqezThemeProvider.colors.parchmentDark,
                        RoundedCornerShape(8.dp)
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        tint = BoqezThemeProvider.colors.inkWarmDim
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = BoqezThemeProvider.colors.goldBase.copy(alpha = 0.35f)
            )
            //Game type selector section

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GameType.entries.forEach { type ->
                    GameTypeCard(
                        modifier = Modifier.weight(1f),
                        type = type,
                        isSelected = type == state.gameType
                    ){
                        viewModel.onEvent(LobbyEvent.OnChangeGameType(type))
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = BoqezThemeProvider.colors.goldBase.copy(alpha = 0.35f)
            )
            //Teams section
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, BoqezThemeProvider.colors.inkBase)
                        .padding(10.dp)
                ) {
                    state.teamA.forEach { player ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BoqezThemeProvider.colors.primaryLightest)
                        ) {
                            Text(
                                text = player.username,
                                color = BoqezThemeProvider.colors.inkBase
                            )
                        }
                    }
                    if (state.myTeamId != 1 && state.teamA.size < 2) {
                        Text(
                            text = "Join team A",
                            modifier = Modifier
                                .noRippleClickable {
                                    viewModel.onEvent(LobbyEvent.OnTeamChange(1))
                                }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, BoqezThemeProvider.colors.inkBase)
                        .padding(10.dp)
                ) {
                    state.teamB.forEach { player ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BoqezThemeProvider.colors.skyLightest)
                        ) {
                            Text(
                                text = player.username,
                                color = BoqezThemeProvider.colors.inkBase
                            )
                        }
                    }
                    if (state.myTeamId != 2 && state.teamB.size < 2) {
                        Text(
                            text = "Join team B",
                            modifier = Modifier
                                .noRippleClickable {
                                    viewModel.onEvent(LobbyEvent.OnTeamChange(2))
                                }
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BoqezThemeProvider.colors.inkBase)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.noTeam.forEach { player ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BoqezThemeProvider.colors.skyLightest)
                    ) {
                        Text(
                            text = player.username,
                            color = BoqezThemeProvider.colors.primaryBase
                        )
                    }
                }
                if (state.myTeamId != null) {
                    Text(
                        text = "Leave team",
                        modifier = Modifier
                            .noRippleClickable {
                                viewModel.onEvent(LobbyEvent.OnTeamChange(null))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(
                onClick = {
                    viewModel.onEvent(if (state.isOwner) LobbyEvent.OnStartGame else LobbyEvent.OnReadyChange)
                },
                isEnabled = (state.isOwner && state.canStartGame) || !state.isOwner
            ) {
                Text(
                    text = if (state.isOwner) "Start" else "Ready"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }

    }
}