package com.lmuro.boqez.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.create
import boqez.composeapp.generated.resources.hint_code
import boqez.composeapp.generated.resources.join
import com.lmuro.boqez.core.utils.Const.LOBBY_CODE_LENGTH
import com.lmuro.boqez.presentation.components.BoqezTextField
import com.lmuro.boqez.presentation.components.PrimaryButton
import com.lmuro.boqez.presentation.components.SecondaryButton
import com.lmuro.boqez.theme.BoqezThemeProvider
import org.jetbrains.compose.resources.stringResource

@Composable
fun LobbyPickerDialog(
    shouldShow: Boolean,
    lobbyCode: String,
    onLobbyCodeChanged: (String) -> Unit,
    onLobbyCreate: () -> Unit,
    onLobbyJoin: () -> Unit,
    onDismissDialog: () -> Unit
) {
    if (shouldShow) {
        Dialog(
            onDismissRequest = onDismissDialog
        ) {
            Box(
                modifier = Modifier
                    .background(BoqezThemeProvider.colors.white, RoundedCornerShape(12.dp))
                    .padding(20.dp)
            ) {
                IconButton(
                    onClick = onDismissDialog,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = BoqezThemeProvider.colors.feltDarkest
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    PrimaryButton(
                        onClick = onLobbyCreate
                    ) {
                        Text(
                            text = stringResource(Res.string.create),
                            style = BoqezThemeProvider.typography.cinzelBold14
                        )
                    }

                    BoqezTextField(
                        value = lobbyCode,
                        placeHolder = stringResource(Res.string.hint_code),
                        onValueChange = onLobbyCodeChanged
                    )

                    SecondaryButton(
                        onClick = onLobbyJoin,
                        isEnabled = lobbyCode.length == LOBBY_CODE_LENGTH
                    ) {
                        Text(
                            text = stringResource(Res.string.join),
                            style = BoqezThemeProvider.typography.cinzelBold14
                        )
                    }
                }
            }
        }
    }
}