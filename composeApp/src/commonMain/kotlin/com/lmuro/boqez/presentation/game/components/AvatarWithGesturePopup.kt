package com.lmuro.boqez.presentation.game.components
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.core.utils.Gesture
import com.lmuro.boqez.domain.model.ActiveGesture
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun AvatarWithGesturePopup(
    gesture: ActiveGesture?,
    borderColor: Color,
    pulse: Boolean = false,
    showPopup: Boolean,
    onAvatarClick: () -> Unit,
    onGesture: (Gesture) -> Unit,
) {
    Box(contentAlignment = Alignment.TopCenter) {
        Box(modifier = Modifier.clickable { onAvatarClick() }) {
            Avatar(
                gesture = gesture,
                borderColor = borderColor,
                pulse = pulse,
            )
        }

        AnimatedVisibility(
            visible = showPopup,
            enter = fadeIn() + scaleIn(
                animationSpec = spring(dampingRatio = 0.5f, stiffness = 600f),
                initialScale = 0.7f
            ),
            exit = fadeOut() + scaleOut(targetScale = 0.7f),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-52).dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = BoqezThemeProvider.colors.feltDark,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = BoqezThemeProvider.colors.goldDark,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                GestureButton(emoji = "✊") { onGesture(Gesture.KNOCK) }
                GestureButton(emoji = "🤚") { onGesture(Gesture.SLASH) }
            }
        }
    }
}

