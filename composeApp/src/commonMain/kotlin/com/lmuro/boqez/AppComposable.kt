package com.lmuro.boqez

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lmuro.boqez.core.navigation.BoqezAppNavigation
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.core.utils.CustomModifiers
import com.lmuro.boqez.core.utils.rememberAppState
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun AppComposable() {
    val navigator = koinInject<Navigator>()
    val startDestination = Screen.LoginScreen
    MaterialTheme {
        val appState = rememberAppState()

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            snackbarHost = {
                CustomModifiers.snackBarHost(appState.scaffoldState)
            }
        ) {
            BoqezAppNavigation(
                navigator = navigator,
                navController = appState.navController,
                startDestination = startDestination,
                showSnackBar = { message ->
                    Napier.v(message, null, "SNACKBAR")
                    appState.showSnackBar(message)
                }
            )
        }
    }
}