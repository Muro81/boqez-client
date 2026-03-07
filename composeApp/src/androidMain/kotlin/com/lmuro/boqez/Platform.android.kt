package com.lmuro.boqez

import android.content.Context
import android.os.Build
import android.provider.Settings

class AndroidPlatform(private val context : Context) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val deviceName: String = "${Build.MANUFACTURER} ${Build.MODEL}"
    override val deviceId: String = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )
}