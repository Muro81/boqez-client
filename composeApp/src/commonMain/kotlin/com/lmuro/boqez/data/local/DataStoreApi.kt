package com.lmuro.boqez.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreApi(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun <T> read(preferenceKey: Preferences.Key<T>) =
        dataStore.data.first().toPreferences()[preferenceKey]

    suspend fun <T> update(preferenceKey: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    suspend fun <T> delete(preferenceKey: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences.remove(preferenceKey)
        }
    }

    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun <T> readAsFlow(preferenceKey : Preferences.Key<T>) = dataStore
        .data
        .map { prefs -> prefs[preferenceKey] }
}