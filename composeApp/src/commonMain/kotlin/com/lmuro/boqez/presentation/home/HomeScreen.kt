package com.lmuro.boqez.presentation.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    showSnackBar :(String) -> Unit
){
    Text(
        text = "HOME SCREEN"
    )
}