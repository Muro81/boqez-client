package com.lmuro.boqez.di

import com.lmuro.boqez.Platform
import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.core.navigation.utils.NavigatorImpl
import com.lmuro.boqez.data.remote.provideKtorClient
import com.lmuro.boqez.data.remote.provideWsClient
import com.lmuro.boqez.data.remote.services.ApiService
import com.lmuro.boqez.data.remote.services.WSService
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect val platformAppModule: Module

val appModule = module {
    single(named("httpClient")) { provideKtorClient(get(), get()) }
    single(named("wsClient")) { provideWsClient() }
    single { ApiService(get(named("httpClient"))) }
    single { WSService(get(named("wsClient"))) }
    single<Navigator> { NavigatorImpl() }
    single<Platform> { get() }
    includes(platformAppModule)
}
