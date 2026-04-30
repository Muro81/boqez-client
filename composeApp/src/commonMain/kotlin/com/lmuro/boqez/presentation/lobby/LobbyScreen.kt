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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.room_temp
import boqez.composeapp.generated.resources.spectators
import boqez.composeapp.generated.resources.teams
import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.ObserveWithLifecycle
import com.lmuro.boqez.core.utils.capitalize
import com.lmuro.boqez.core.utils.noRippleClickable
import com.lmuro.boqez.presentation.base.BaseContentView
import com.lmuro.boqez.presentation.components.PrimaryButton
import com.lmuro.boqez.presentation.lobby.components.GameTypeCard
import com.lmuro.boqez.presentation.lobby.components.SpectatorList
import com.lmuro.boqez.presentation.lobby.components.TeamList
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

    val maxTeamSize = when (state.gameType) {
        GameType.BRISKULA, GameType.TRESETA -> 2
        GameType.TERCULJA -> 3
        null -> 0
    }
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
                    style = BoqezThemeProvider.typography.cinzelBold24,
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
                        .noRippleClickable {
                            //TODO add copy text to clipboard
                        }

                ) {
                    Text(
                        text = stringResource(Res.string.room_temp, state.lobbyId).uppercase(),
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
            if (state.isOwner) {
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
                        ) {
                            viewModel.onEvent(LobbyEvent.OnChangeGameType(type))
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = BoqezThemeProvider.colors.goldBase.copy(alpha = 0.35f)
                )
            }
            //Teams section
            if (state.gameType != null)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = BoqezThemeProvider.colors.goldBase.copy(alpha = 0.35f)
                    )
                    Text(
                        text = stringResource(Res.string.teams),
                        style = BoqezThemeProvider.typography.cinzelBold20,
                        color = BoqezThemeProvider.colors.goldDark
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = BoqezThemeProvider.colors.goldBase.copy(alpha = 0.35f)
                    )
                }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                if (state.gameType == GameType.TERCULJA) {
                    TeamList(
                        modifier = Modifier
                            .weight(1f),
                        teamName = "KUPA",
                        headerColor = BoqezThemeProvider.colors.crimsonBase,
                        maxTeamSize = maxTeamSize,
                        players = state.players,
                        currentUserId = state.userId,
                        ownerId = state.ownerId,
                        canJoinTeam = false,
                        onJoinTeam = {},
                        onKickPlayer = {}
                    )
                } else if (state.gameType != null) {
                    TeamList(
                        modifier = Modifier
                            .weight(1f),
                        teamName = "KUPA",
                        headerColor = BoqezThemeProvider.colors.crimsonBase,
                        maxTeamSize = maxTeamSize,
                        players = state.teamA,
                        currentUserId = state.userId,
                        ownerId = state.ownerId,
                        canJoinTeam = state.myTeamId != 1,
                        onJoinTeam = {
                            viewModel.onEvent(LobbyEvent.OnTeamChange(1))
                        },
                        onKickPlayer = {}
                    )
                    TeamList(
                        modifier = Modifier
                            .weight(1f),
                        teamName = "SPADA",
                        headerColor = BoqezThemeProvider.colors.feltLight,
                        maxTeamSize = maxTeamSize,
                        players = state.teamB,
                        currentUserId = state.userId,
                        ownerId = state.ownerId,
                        canJoinTeam = state.myTeamId != 2,
                        onJoinTeam = {
                            viewModel.onEvent(LobbyEvent.OnTeamChange(2))
                        },
                        onKickPlayer = {}
                    )
                }
            }
            //Spectator section
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = BoqezThemeProvider.colors.goldBase.copy(alpha = 0.35f)
                )
                Text(
                    text = stringResource(Res.string.spectators),
                    style = BoqezThemeProvider.typography.cinzelBold20,
                    color = BoqezThemeProvider.colors.goldDark
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = BoqezThemeProvider.colors.goldBase.copy(alpha = 0.35f)
                )
            }

            SpectatorList(
                spectators = state.players.filter { it.teamId == null },
                currentUserId = state.userId
            ) {
                viewModel.onEvent(LobbyEvent.OnTeamChange(null))
            }

            Spacer(modifier = Modifier.weight(1f))
            if (state.myTeamId != null) {
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
            }
            Spacer(modifier = Modifier.weight(1f))
        }

    }
}