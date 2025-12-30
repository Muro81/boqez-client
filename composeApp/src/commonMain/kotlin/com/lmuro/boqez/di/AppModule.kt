package com.lmuro.boqez.di

import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.core.navigation.utils.NavigatorImpl
import com.lmuro.boqez.data.remote.provideKtorClient
import com.lmuro.boqez.data.remote.services.ApiService
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformAppModule : Module

val appModule = module {
    single { provideKtorClient(get()) }
    single{ ApiService(get()) }
    single<Navigator>{ NavigatorImpl() }
    includes(platformAppModule)
}
