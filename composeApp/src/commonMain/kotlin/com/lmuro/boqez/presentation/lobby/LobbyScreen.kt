package com.lmuro.boqez.presentation.lobby

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.lmuro.boqez.core.utils.ObserveWithLifecycle
import com.lmuro.boqez.presentation.base.BaseContentView
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LobbyScreen(
    viewModel: LobbyViewModel = koinViewModel(),
    showSnackBar: (String) -> Unit
) {
    viewModel.snackBarChanel.ObserveWithLifecycle {
        showSnackBar(it)
    }
    val state by viewModel.stateFlow.collectAsState()
    BaseContentView(
        state = state
    ) {

    }
}