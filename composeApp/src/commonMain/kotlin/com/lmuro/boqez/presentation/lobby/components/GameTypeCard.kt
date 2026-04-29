package com.lmuro.boqez.presentation.lobby.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.player_temp
import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.capitalize
import com.lmuro.boqez.theme.BoqezThemeProvider
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameTypeCard(
    modifier: Modifier,
    type: GameType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        if (isSelected) BoqezThemeProvider.colors.crimsonBase else BoqezThemeProvider.colors.white
    val textColor =
        if (isSelected) BoqezThemeProvider.colors.goldLight else BoqezThemeProvider.colors.inkDarkest
    val supportTextColor =
        if (isSelected) BoqezThemeProvider.colors.goldDark else BoqezThemeProvider.colors.inkDarkest
    val playerInfoText = when (type) {
        GameType.BRISKULA, GameType.TRESETA -> "4"
        GameType.TERCULJA -> "3"
    }
    //TODO work on colors here looks like shit
    //TODO implement custom string formating so it works ffs
    Card(
        modifier = modifier
            .fillMaxHeight(),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = type.gameName.capitalize(),
                style = BoqezThemeProvider.typography.garamondMedium14,
                color = textColor
            )
            Text(
                text = stringResource(Res.string.player_temp, playerInfoText),
                style = BoqezThemeProvider.typography.garamondItalic12,
                color = supportTextColor
            )
        }
    }
}