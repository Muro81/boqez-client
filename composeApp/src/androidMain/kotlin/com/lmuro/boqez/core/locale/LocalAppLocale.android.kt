package com.lmuro.boqez.core.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

actual object LocalAppLocale {
    private var defaultLocale: Locale? = null
    actual val current: String
        @Composable get() = Locale.getDefault().toString()

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        val configuration = LocalConfiguration.current

        if (defaultLocale == null) {
            defaultLocale = Locale.getDefault()
        }

        val newLocale = when {
            value == null -> defaultLocale!!
            value.contains("me") -> Locale.Builder()
                .setLanguageTag("sr-Latn-ME")
                .setScript("Latn")
                .setRegion("ME")
                .build()

            value.contains("en") -> Locale.Builder()
                .setLanguageTag("en")
                .setScript("Latn")
                .build()

            else -> Locale.Builder()
                .setLanguageTag(value)
                .build()
        }

        Locale.setDefault(newLocale)
        configuration.setLocale(newLocale)

        val context = LocalContext.current
        context.createConfigurationContext(configuration)

        return LocalConfiguration provides configuration
    }
}