package com.lmuro.boqez.domain.repository

import com.lmuro.boqez.core.networking.NetworkError
import com.lmuro.boqez.core.networking.Resource
import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.Gesture
import com.lmuro.boqez.domain.model.Auth
import com.lmuro.boqez.domain.model.LobbyJoin
import com.lmuro.boqez.domain.model.UserInfo

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

    suspend fun getUser(): Resource<UserInfo, NetworkError, String?>

    suspend fun createLobby(
        isPublic: Boolean,
        gameType: GameType?
    ): Resource<String, NetworkError, String?>

    suspend fun joinLobby(lobbyId: String): Resource<LobbyJoin, NetworkError, String?>

    suspend fun leaveTeam(lobbyId: String): Resource<Any, NetworkError, String?>

    suspend fun changeTeam(lobbyId: String, teamId: Int): Resource<Any, NetworkError, String?>

    suspend fun changeReady(lobbyId: String, isReady: Boolean): Resource<Any, NetworkError, String?>

    suspend fun leaveLobby(lobbyId: String): Resource<Any, NetworkError, String?>

    suspend fun changeGameType(
        lobbyId: String,
        gameType: GameType
    ): Resource<Any, NetworkError, String?>

    suspend fun startGame(lobbyId: String): Resource<Any, NetworkError, String?>

    suspend fun sendGesture(gameId: String, gesture: Gesture): Resource<Any, NetworkError, String?>
}


