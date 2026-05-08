package com.lmuro.boqez.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.lmuro.boqez.IOSPlatform
import com.lmuro.boqez.data.local.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module
import com.lmuro.boqez.Platform
import com.lmuro.boqez.core.utils.ClipboardManager


actual val platformAppModule: Module = module{
    single<DataStore<Preferences>> { createDataStore() }
    single<Platform> { IOSPlatform() }
    single { ClipboardManager() }
}