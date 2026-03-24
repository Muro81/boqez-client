package com.lmuro.boqez

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lmuro.boqez.core.locale.LocalAppLocale
import com.lmuro.boqez.core.navigation.BoqezAppNavigation
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.core.utils.CustomModifiers
import com.lmuro.boqez.core.utils.rememberAppState
import com.lmuro.boqez.data.local.DataStoreApi
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.ACCESS_TOKEN
import com.lmuro.boqez.presentation.viewmodels.AppViewModel
import com.lmuro.boqez.presentation.viewmodels.LanguageViewModel
import com.lmuro.boqez.theme.BoqezTheme
import io.github.aakira.napier.Napier
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
@Composable
@Preview
fun AppComposable(
    languageViewModel: LanguageViewModel = koinViewModel(),
    appViewModel : AppViewModel = koinViewModel()
) {
    val languageCode by languageViewModel.languageCode.collectAsStateWithLifecycle()
    val navigator = koinInject<Navigator>()
    val dataStoreApi = koinInject<DataStoreApi>()

    val hasSession = runBlocking { dataStoreApi.read(ACCESS_TOKEN)}
    val startDestination = if(hasSession == null) Screen.LoginScreen else Screen.HomeScreen
    val appState = rememberAppState()
    CompositionLocalProvider(
        LocalAppLocale provides languageCode
    ) {
        key(languageCode) {
            BoqezTheme {
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
                        },
                        switchLanguage = { lang ->
                            languageViewModel.switchLanguage(lang)
                        }
                    )
                }
            }
        }
    }
}