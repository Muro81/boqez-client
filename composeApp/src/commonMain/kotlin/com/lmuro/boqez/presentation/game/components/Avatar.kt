package com.lmuro.boqez.presentation.game.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.img_avatar
import com.lmuro.boqez.core.utils.Gesture
import com.lmuro.boqez.domain.model.ActiveGesture
import org.jetbrains.compose.resources.painterResource

@Composable
fun Avatar(
    gesture: ActiveGesture?,
    borderColor : Color,
) {
    val isWinking = gesture?.gesture == Gesture.THREE
    val isPouting = gesture?.gesture == Gesture.ACE
    val isEyebrowUp = gesture?.gesture == Gesture.KING
    val isTongueOut = gesture?.gesture == Gesture.JACK
    val isShrugging = gesture?.gesture == Gesture.KNIGHT
    val isKnock = gesture?.gesture == Gesture.KNOCK
    val isSlash = gesture?.gesture == Gesture.SLASH

    val shrugAmount by animateFloatAsState(
        targetValue = if (isShrugging) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.3f, stiffness = 400f),
        label = "shrug"
    )

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .border(1.dp,borderColor,CircleShape)
    ) {
        Image(
            painter = painterResource(Res.drawable.img_avatar),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .graphicsLayer { translationY = -shrugAmount * 4.dp.toPx() }
        )

        WinkOverlay(active = isWinking)
        PoutOverlay(active = isPouting)
        EyebrowOverlay(active = isEyebrowUp)
        TongueOverlay(active = isTongueOut)

        // --- SVG overlays (appear beside avatar) ---
        FistOverlay(active = isKnock)
        PalmOverlay(active = isSlash)
    }
}

@Composable
fun WinkOverlay(active: Boolean) {
    val closedAmount by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = tween(120, easing = FastOutSlowInEasing),
        label = "wink"
    )

    Canvas(
        modifier = Modifier
            .size(50.dp)
            .offset(x = (-6).dp, y = 16.dp) // tune to sit over left eye
    ) {
        if (closedAmount > 0f) {
            val eyeWidth = 5.dp.toPx()
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            // Draw a curved line (closed eye) that fades in
            drawArc(
                color = Color(0xFF3D2B1F).copy(alpha = closedAmount),
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(centerX - eyeWidth / 2, centerY - 1.dp.toPx()),
                size = Size(eyeWidth, 3.dp.toPx()),
                style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

// Pout — draws slightly puffed lips over the mouth
@Composable
fun PoutOverlay(active: Boolean) {
    val poutAmount by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = tween(150, easing = FastOutSlowInEasing),
        label = "pout"
    )

    Canvas(
        modifier = Modifier
            .size(50.dp)
            .offset(x = 0.dp, y = 28.dp) // tune to sit over mouth
    ) {
        if (poutAmount > 0f) {
            val mouthWidth = 7.dp.toPx() * (1f + poutAmount * 0.2f)
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            // Upper lip arc
            drawArc(
                color = Color(0xFFB05070).copy(alpha = poutAmount),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(centerX - mouthWidth / 2, centerY - 2.dp.toPx()),
                size = Size(mouthWidth, 3.dp.toPx()),
                style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
            )
            // Lower lip arc
            drawArc(
                color = Color(0xFFB05070).copy(alpha = poutAmount),
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(centerX - mouthWidth / 2, centerY - 0.5.dp.toPx()),
                size = Size(mouthWidth, 4.dp.toPx()),
                style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

// Eyebrow raise — translates both brows upward slightly
@Composable
fun EyebrowOverlay(active: Boolean) {
    val raiseAmount by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.4f, stiffness = 300f),
        label = "eyebrow"
    )

    Canvas(
        modifier = Modifier
            .size(50.dp)
            .offset(x = 0.dp, y = 12.dp) // tune to sit over brows
            .graphicsLayer {
                translationY = -raiseAmount * 3.dp.toPx()
                alpha = raiseAmount
            }
    ) {
        val browWidth = 5.dp.toPx()
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        // Left brow
        drawLine(
            color = Color(0xFF3D2B1F),
            start = Offset(centerX - 8.dp.toPx(), centerY),
            end = Offset(centerX - 8.dp.toPx() + browWidth, centerY - 1.dp.toPx()),
            strokeWidth = 1.5.dp.toPx(),
            cap = StrokeCap.Round
        )
        // Right brow
        drawLine(
            color = Color(0xFF3D2B1F),
            start = Offset(centerX + 3.dp.toPx(), centerY - 1.dp.toPx()),
            end = Offset(centerX + 3.dp.toPx() + browWidth, centerY),
            strokeWidth = 1.5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

// Tongue — slides down from behind the mouth
@Composable
fun TongueOverlay(active: Boolean) {
    val extendAmount by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = tween(200, easing = FastOutSlowInEasing),
        label = "tongue"
    )

    Canvas(
        modifier = Modifier
            .size(50.dp)
            .offset(x = 0.dp, y = 30.dp) // tune to sit at mouth bottom
    ) {
        if (extendAmount > 0f) {
            val tongueWidth = 4.dp.toPx()
            val tongueHeight = 5.dp.toPx() * extendAmount
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            drawRoundRect(
                color = Color(0xFFE87090).copy(alpha = extendAmount),
                topLeft = Offset(centerX - tongueWidth / 2, centerY),
                size = Size(tongueWidth, tongueHeight),
                cornerRadius = CornerRadius(tongueWidth / 2),
            )
        }
    }
}

@Composable
fun FistOverlay(active: Boolean) {
    val scale by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 500f),
        label = "fist"
    )

    if (scale > 0f) {
        Text(
            text = "✊",
            fontSize = 16.sp,
            modifier = Modifier
                .offset(x = 30.dp, y = 20.dp)
                .graphicsLayer { scaleX = scale; scaleY = scale }
        )
    }
}

@Composable
fun PalmOverlay(active: Boolean) {
    val scale by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 500f),
        label = "slash"
    )

    if (scale > 0f) {
        Text(
            text = "\uD83E\uDD1A",
            fontSize = 16.sp,
            modifier = Modifier
                .offset(x = 30.dp, y = 20.dp)
                .graphicsLayer { scaleX = scale; scaleY = scale }
        )
    }
}