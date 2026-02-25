package com.lmuro.boqez.core.locale

import java.util.Locale

actual fun getDefaultLocale(): String {
    return Locale.getDefault().toString()
}