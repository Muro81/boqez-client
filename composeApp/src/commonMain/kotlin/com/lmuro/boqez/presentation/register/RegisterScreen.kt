package com.lmuro.boqez.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.lmuro.boqez.core.utils.ObserveWithLifecycle
import com.lmuro.boqez.presentation.base.BaseContentView
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = koinViewModel(),
    showSnackBar : (String) -> Unit
){
    val state by viewModel.stateFlow.collectAsState()
    viewModel.snackBarChanel.ObserveWithLifecycle {
        showSnackBar(it)
    }

    BaseContentView(
        state = state
    ) {

    }

}