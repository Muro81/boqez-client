package com.lmuro.boqez.presentation.home

import androidx.lifecycle.viewModelScope
import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.domain.repository.BoqezRepository
import com.lmuro.boqez.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BoqezRepository,
    private val navigator: Navigator
) : BaseViewModel<HomeState, HomeEvent>() {
    override val initialState: HomeState = HomeState()
    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnAboutUsClick -> TODO()
            HomeEvent.OnPlayClick -> TODO()
            HomeEvent.OnProfileClick -> TODO()
            HomeEvent.OnSettingsClick -> TODO()
        }
    }

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            repository.getUser()
        }
    }
}