package com.lmuro.boqez.presentation.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.lmuro.boqez.core.utils.noRippleClickable
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun BaseContentView(
    modifier: Modifier = Modifier,
    state: BaseState,
    topBar: @Composable () -> Unit = {},
    fab: @Composable () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    val focusManager = LocalFocusManager.current

    val customModifier = remember {
        modifier
            .fillMaxSize()
            .noRippleClickable {
                focusManager.clearFocus()
            }
    }

    Scaffold(
        modifier = customModifier,
        topBar = topBar,
        floatingActionButton = fab
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            content()
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize().background(BoqezThemeProvider.colors.parchment)) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(
                            alignment = Alignment.Center
                        ),
                        color = BoqezThemeProvider.colors.goldDark
                    )
                }
            }
        }
        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
    }
}