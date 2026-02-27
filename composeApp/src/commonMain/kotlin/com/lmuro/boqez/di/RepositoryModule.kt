package com.lmuro.boqez.di

import com.lmuro.boqez.data.local.DataStoreApi
import com.lmuro.boqez.data.repository.BoqezRepositoryImpl
import com.lmuro.boqez.domain.repository.BoqezRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { DataStoreApi(get()) }
    single<BoqezRepository> { BoqezRepositoryImpl(get()) }
}