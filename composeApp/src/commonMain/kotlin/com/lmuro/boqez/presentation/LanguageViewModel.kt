package com.lmuro.boqez.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmuro.boqez.core.locale.getDefaultLocale
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.USER_PREFERRED_LANGUAGE
import com.lmuro.boqez.data.local.DataStoreApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LanguageViewModel(
    private val dataStoreApi: DataStoreApi
) : ViewModel() {

    val languageCode = dataStoreApi.readAsFlow(
        USER_PREFERRED_LANGUAGE
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        getDefaultLocale()
    )

    fun switchLanguage(languageCode : String){
        viewModelScope.launch {
            dataStoreApi.update(
                USER_PREFERRED_LANGUAGE,
                languageCode
            )
        }
    }
}