package com.lmuro.boqez.core.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue

expect object LocalAppLocale {
    val current: String @Composable get
    @Composable
    infix fun provides(value: String?): ProvidedValue<*>
}