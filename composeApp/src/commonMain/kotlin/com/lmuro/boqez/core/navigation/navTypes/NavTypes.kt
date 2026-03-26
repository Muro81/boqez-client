package com.lmuro.boqez.core.navigation.navTypes

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.domain.model.LobbyUser
import kotlin.reflect.typeOf

// Put this somewhere shared, e.g. NavTypes.kt
val lobbyScreenTypeMap = mapOf(
    typeOf<GameType?>() to GameTypeNavType,
    typeOf<List<LobbyUser>>() to LobbyUserListNavType
)