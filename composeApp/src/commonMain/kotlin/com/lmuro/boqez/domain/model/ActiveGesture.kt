package com.lmuro.boqez.domain.model

import com.lmuro.boqez.core.utils.Gesture
import kotlin.time.Clock

data class ActiveGesture(
    val gesture: Gesture,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds()
)
