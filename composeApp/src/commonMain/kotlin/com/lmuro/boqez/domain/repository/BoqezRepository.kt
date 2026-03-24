package com.lmuro.boqez.domain.repository

import com.lmuro.boqez.core.networking.NetworkError
import com.lmuro.boqez.core.networking.Resource
import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.domain.model.Auth
import com.lmuro.boqez.domain.model.LobbyJoin

interface BoqezRepository {
    suspend fun login(
        email: String,
        password: String,
        device: String
    ): Resource<Auth, NetworkError, String?>

    suspend fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        device: String
    ): Resource<Auth, NetworkError, String?>

    suspend fun getUser(): Resource<Any, NetworkError, String?>

    suspend fun createLobby(
        isPublic: Boolean,
        gameType: GameType?
    ): Resource<String, NetworkError, String?>

    suspend fun joinLobby(lobbyId: String): Resource<LobbyJoin, NetworkError, String?>
}