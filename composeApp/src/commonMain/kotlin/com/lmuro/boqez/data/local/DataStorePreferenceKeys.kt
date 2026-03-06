package com.lmuro.boqez.data.local

import androidx.datastore.preferences.core.stringPreferencesKey

class DataStorePreferenceKeys {
    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val USER_PREFERRED_LANGUAGE = stringPreferencesKey("USER_PREFERRED_LANGUAGE")
        val DEVICE_NAME = stringPreferencesKey("DEVICE_NAME")
        val DEVICE_ID = stringPreferencesKey("DEVICE_ID")
    }
}