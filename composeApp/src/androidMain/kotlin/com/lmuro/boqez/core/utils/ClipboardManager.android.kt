package com.lmuro.boqez.core.utils

import android.content.ClipData
import android.content.ClipboardManager as AndroidClipboardManager
import android.content.Context

actual class ClipboardManager(private val context: Context) {
    actual fun copyToClipboard(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as AndroidClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("lobby_code", text))
    }
}