package com.lmuro.boqez.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.lmuro.boqez.core.navigation.destinations.homeComposable
import com.lmuro.boqez.core.navigation.destinations.loginComposable
import com.lmuro.boqez.core.navigation.destinations.registerComposable
import com.lmuro.boqez.core.navigation.utils.NavType
import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.core.navigation.utils.ObserveAsEvents
import io.github.aakira.napier.Napier

@Composable
fun BoqezAppNavigation(
    navController: NavHostController,
    navigator: Navigator,
    startDestination: Screen,
    showSnackBar: (String) -> Unit,
    switchLanguage : (String) -> Unit
) {
    ObserveAsEvents(flow = navigator.navigationFlow) { navType ->
        Napier.v("NAVTYPE $navType")
        when (navType) {
            is NavType.NavigateToRoute -> {
                navController.navigate(route = navType.destination) {
                    launchSingleTop = true
                    restoreState = true
                    navType.navOptions(this)
                }
            }

            NavType.NavigateUp -> {
                if (navController.previousBackStackEntry != null) {
                    navController.navigateUp()
                }
            }
        }
    }

    NavHost(
        route = Screen.ROOT::class,
        startDestination = startDestination,
        navController = navController
    ) {
        loginComposable(showSnackBar = showSnackBar, switchLanguage = switchLanguage)
        registerComposable(showSnackBar = showSnackBar)
        homeComposable(showSnackBar = showSnackBar)
    }
}