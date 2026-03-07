package com.lmuro.boqez.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmuro.boqez.core.locale.getDefaultLocale
import com.lmuro.boqez.data.local.DataStoreApi
import com.lmuro.boqez.data.local.DataStorePreferenceKeys
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LanguageViewModel(
    private val dataStoreApi: DataStoreApi
) : ViewModel() {

    val languageCode = dataStoreApi.readAsFlow(
        DataStorePreferenceKeys.USER_PREFERRED_LANGUAGE
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        getDefaultLocale()
    )

    fun switchLanguage(languageCode: String) {
        viewModelScope.launch {
            dataStoreApi.update(
                DataStorePreferenceKeys.USER_PREFERRED_LANGUAGE,
                languageCode
            )
        }
    }
}