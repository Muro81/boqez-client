package com.lmuro.boqez.presentation.lobby

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FullscreenExit
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
import com.lmuro.boqez.theme.BoqezThemeProvider
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
    val isOwner = state.ownerId == state.userId
    val teamA = state.players.filter { it.teamId == 1 }
    val teamB = state.players.filter { it.teamId == 2 }
    val noTeam = state.players.filter { it.teamId == null }
    val myTeam = state.players.find { it.userId == state.userId }?.teamId

    BaseContentView(
        state = state
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(LobbyEvent.OnLeaveGame)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        tint = BoqezThemeProvider.colors.primaryDarkest
                    )
                }
            }
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
                    teamA.forEach { player ->
                        Text(
                            text = player.username
                        )
                    }
                    if(myTeam != 1 && teamA.size < 2){
                        Text(
                            text = "Join team A"
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, BoqezThemeProvider.colors.inkBase)
                        .padding(10.dp)
                ) {
                    teamB.forEach { player ->
                        Text(
                            text = player.username
                        )
                    }
                    if(myTeam != 2 && teamB.size < 2){
                        Text(
                            text = "Join team B"
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                noTeam.forEach { player ->
                    Text(
                        text = player.username
                    )
                }
                if(myTeam != null){
                    Text(
                        text = "Leave team"
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(
                onClick = {
                    viewModel.onEvent(if (isOwner) LobbyEvent.OnStartGame else LobbyEvent.OnReadyChange)
                },
                isEnabled = (isOwner && state.players.all { it.isReady }) || !isOwner
            ) {
                Text(
                    text = if (isOwner) "Start" else "Ready"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }

    }
}