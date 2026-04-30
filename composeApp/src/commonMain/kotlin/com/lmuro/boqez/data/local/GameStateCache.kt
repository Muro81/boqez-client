package com.lmuro.boqez.data.local

import com.lmuro.boqez.domain.model.GameStartData

class GameStateCache {
    private val cache = mutableMapOf<String,GameStartData>()

    fun put(gameId : String, data : GameStartData){ cache[gameId] = data}
    fun get(gameId : String): GameStartData? = cache[gameId]
    fun remove(gameId : String) {cache.remove(gameId)}
}