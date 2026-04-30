package com.lmuro.boqez.data.repository

import com.lmuro.boqez.core.networking.NetworkError
import com.lmuro.boqez.core.networking.Resource
import com.lmuro.boqez.core.networking.map
import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.data.remote.dto.requests.AuthRequestDto
import com.lmuro.boqez.data.remote.dto.requests.ChangeGameTypeRequestDto
import com.lmuro.boqez.data.remote.dto.requests.ChangeTeamRequestDto
import com.lmuro.boqez.data.remote.dto.requests.LobbyCreateRequestDto
import com.lmuro.boqez.data.remote.dto.requests.LobbyWrapperRequestDto
import com.lmuro.boqez.data.remote.dto.requests.ReadyStatusRequestDto
import com.lmuro.boqez.data.remote.dto.requests.RegisterRequestDto
import com.lmuro.boqez.data.remote.mappers.toAuth
import com.lmuro.boqez.data.remote.mappers.toJoinLobby
import com.lmuro.boqez.data.remote.mappers.toUserInfo
import com.lmuro.boqez.data.remote.services.ApiService
import com.lmuro.boqez.domain.model.Auth
import com.lmuro.boqez.domain.model.LobbyJoin
import com.lmuro.boqez.domain.model.UserInfo
import com.lmuro.boqez.domain.repository.BoqezRepository

class BoqezRepositoryImpl(
    private val apiService: ApiService
) : BoqezRepository {
    override suspend fun login(
        email: String,
        password: String,
        device: String
    ): Resource<Auth, NetworkError, String?> {
        return apiService.login(
            body = AuthRequestDto(
                email = email,
                password = password,
                device = device
            )
        ).map {
            it.data.toAuth()
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        device: String
    ): Resource<Auth, NetworkError, String?> {
        return apiService.register(
            body = RegisterRequestDto(
                email = email,
                username = username,
                password = password,
                device = device,
                confirmPassword = confirmPassword
            )
        ).map {
            it.data.toAuth()
        }
    }

    override suspend fun getUser(): Resource<UserInfo, NetworkError, String?> {
        return apiService.getUser().map {
            it.data.toUserInfo()
        }
    }

    override suspend fun createLobby(
        isPublic: Boolean,
        gameType: GameType?
    ): Resource<String, NetworkError, String?> {
        return apiService.createLobby(
            body = LobbyCreateRequestDto(
                isPublic = isPublic,
                gameType = gameType
            )
        ).map { it.data.id }
    }

    override suspend fun joinLobby(lobbyId: String): Resource<LobbyJoin, NetworkError, String?> {
        return apiService.joinLobby(
            body = LobbyWrapperRequestDto(
                lobbyId = lobbyId
            )
        ).map { it.data.toJoinLobby() }
    }

    override suspend fun changeReady(
        lobbyId: String,
        isReady: Boolean
    ): Resource<Any, NetworkError, String?> {
        return apiService.changeReady(
            body = ReadyStatusRequestDto(
                isReady = isReady,
                lobbyId = lobbyId
            )
        )
    }

    override suspend fun changeTeam(
        lobbyId: String,
        teamId: Int
    ): Resource<Any, NetworkError, String?> {
        return apiService.changeTeam(
            body = ChangeTeamRequestDto(
                lobbyId = lobbyId,
                teamId = teamId
            )
        )
    }

    override suspend fun leaveLobby(lobbyId: String): Resource<Any, NetworkError, String?> {
        return apiService.leaveLobby(
            body = LobbyWrapperRequestDto(
                lobbyId = lobbyId
            )
        )
    }

    override suspend fun leaveTeam(lobbyId: String): Resource<Any, NetworkError, String?> {
        return apiService.leaveTeam(
            body = LobbyWrapperRequestDto(
                lobbyId = lobbyId
            )
        )
    }

    override suspend fun changeGameType(
        lobbyId: String,
        gameType: GameType
    ): Resource<Any, NetworkError, String?> {
        return apiService.changeGameType(
            body = ChangeGameTypeRequestDto(
                lobbyId = lobbyId,
                gameType = gameType
            )
        )
    }

    override suspend fun startGame(lobbyId: String): Resource<Any, NetworkError, String?> {
        return apiService.startGame(
            body = LobbyWrapperRequestDto(lobbyId = lobbyId)
        )
    }
}