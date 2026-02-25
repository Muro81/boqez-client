package com.lmuro.boqez.core.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.InternalComposeUiApi
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.preferredLanguages

@OptIn(InternalComposeUiApi::class)
actual object LocalAppLocale {

    private val defaultLocale = NSLocale.preferredLanguages.first() as String
    private val LocalAppLocale = staticCompositionLocalOf { defaultLocale }

    actual val current: String
        @Composable get() = LocalAppLocale.current

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        val mappedLocale = when (value) {
            "me" -> "sr-Latn-ME"
            "en" -> "en"
            null -> null
            else -> value
        }

        if (mappedLocale == null) {
            NSUserDefaults.standardUserDefaults.removeObjectForKey("AppleLanguages")
        } else {
            NSUserDefaults.standardUserDefaults.setObject(listOf(mappedLocale), "AppleLanguages")
        }

        val newLocale = mappedLocale ?: defaultLocale
        return LocalAppLocale provides newLocale
    }
}