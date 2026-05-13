package com.lmuro.boqez.presentation.game.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.core.utils.Gesture
import com.lmuro.boqez.core.utils.emoji
import com.lmuro.boqez.core.utils.noRippleClickable
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun CardGestureButton(
    visible: Boolean,
    gesture: Gesture,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(
            animationSpec = spring(dampingRatio = 0.5f, stiffness = 500f),
            initialScale = 0.6f
        ),
        exit = fadeOut() + scaleOut(targetScale = 0.6f),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = BoqezThemeProvider.colors.feltDark,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = BoqezThemeProvider.colors.goldBase,
                    shape = CircleShape
                )
                .noRippleClickable { onClick() }
                .padding(7.dp)
        ) {
            Text(
                text = gesture.emoji(),
                style = BoqezThemeProvider.typography.garamondRegular16
            )
        }
    }
}