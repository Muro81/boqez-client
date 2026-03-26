package com.lmuro.boqez.core.navigation.navTypes

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import com.lmuro.boqez.domain.model.LobbyUser
import kotlinx.serialization.json.Json

object LobbyUserListNavType : NavType<List<LobbyUser>>(isNullableAllowed = false) {
    override fun get(bundle: SavedState, key: String): List<LobbyUser>? =
        bundle.read { getStringOrNull(key) }?.let { Json.decodeFromString(it) }

    override fun parseValue(value: String): List<LobbyUser> =
        Json.decodeFromString(value)

    override fun serializeAsValue(value: List<LobbyUser>): String =
        Json.encodeToString(value)

    override fun put(bundle: SavedState, key: String, value: List<LobbyUser>) {
        bundle.write { putString(key, Json.encodeToString(value)) }
    }
}