package com.lmuro.boqez.core.navigation.navTypes

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import com.lmuro.boqez.core.utils.GameType

object GameTypeNavType : NavType<GameType?>(isNullableAllowed = true) {
    override fun get(bundle: SavedState, key: String): GameType? =
        bundle.read { getStringOrNull(key) }?.let { value ->
            GameType.entries.firstOrNull { it.gameName == value }
        }

    override fun parseValue(value: String): GameType? =
        GameType.entries.firstOrNull  { it.gameName == value }

    override fun serializeAsValue(value: GameType?): String = value?.gameName ?: ""

    override fun put(bundle: SavedState, key: String, value: GameType?) {
        bundle.write { putString(key, value?.gameName ?: "") }
    }
}