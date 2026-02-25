package com.lmuro.boqez.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.lmuro.boqez.data.local.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformAppModule: Module = module{
    single<DataStore<Preferences>> { createDataStore(androidContext()) }
}