package com.lmuro.boqez.di

import com.lmuro.boqez.presentation.home.HomeViewModel
import com.lmuro.boqez.presentation.viewmodels.LanguageViewModel
import com.lmuro.boqez.presentation.login.LoginViewModel
import com.lmuro.boqez.presentation.register.RegisterViewModel
import com.lmuro.boqez.presentation.viewmodels.AppViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::AppViewModel)
    viewModelOf(::LanguageViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::HomeViewModel)
}