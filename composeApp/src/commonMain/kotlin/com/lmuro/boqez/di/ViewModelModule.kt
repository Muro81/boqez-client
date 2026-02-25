package com.lmuro.boqez.di

import com.lmuro.boqez.presentation.LanguageViewModel
import com.lmuro.boqez.presentation.login.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::LanguageViewModel)
}