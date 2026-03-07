package com.lmuro.boqez.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmuro.boqez.Platform
import com.lmuro.boqez.data.local.DataStoreApi
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.DEVICE_ID
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.DEVICE_NAME
import kotlinx.coroutines.launch

class AppViewModel(
    private val dataStoreApi: DataStoreApi,
    private val platform: Platform
) : ViewModel() {

    init {
        viewModelScope.launch {
            saveDeviceInfo()
        }
    }

    private suspend fun saveDeviceInfo() {
        dataStoreApi.update(DEVICE_NAME, platform.deviceName)
        dataStoreApi.update(DEVICE_ID, platform.deviceId)
    }
}