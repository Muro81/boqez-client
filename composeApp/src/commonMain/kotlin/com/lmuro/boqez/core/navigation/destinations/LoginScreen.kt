package com.lmuro.boqez.core.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.presentation.login.LoginScreen

fun NavGraphBuilder.loginComposable(
    showSnackBar : (String) -> Unit
){
    composable<Screen.LoginScreen> {
        LoginScreen(
            showSnackBar = showSnackBar
        )
    }
}