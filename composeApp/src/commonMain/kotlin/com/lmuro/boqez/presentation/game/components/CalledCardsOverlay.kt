package com.lmuro.boqez.presentation.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun CalledCardsOverlay(
    userId: String,
    combos: List<List<Card>>,
    currentUserId: String,
    callerUsername: String,
    modifier: Modifier = Modifier
) {
    val isMe = userId == currentUserId

    Box(
        modifier = modifier.run {
            fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(BoqezThemeProvider.colors.feltDarkest.copy(alpha = 0.85f))
                .border(1.dp, BoqezThemeProvider.colors.goldBase, RoundedCornerShape(12.dp))
                .padding(12.dp)
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (isMe) "You called!" else "$callerUsername called!",
                style = BoqezThemeProvider.typography.cinzelBold14,
                color = BoqezThemeProvider.colors.goldLight,
            )
            combos.forEach { combo ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    combo.forEach { card ->
                        CardFace(
                            card = card,
                            modifier = Modifier.width(36.dp).height(50.dp)
                        )
                    }
                }
            }
        }
    }
}