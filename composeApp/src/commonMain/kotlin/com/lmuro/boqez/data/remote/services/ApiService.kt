package com.lmuro.boqez.data.remote.services

import boqez.composeapp.generated.resources.Res
import com.lmuro.boqez.core.networking.NetworkError
import com.lmuro.boqez.core.networking.Resource
import com.lmuro.boqez.data.remote.dto.DataResponse
import com.lmuro.boqez.data.remote.dto.requests.AuthRequestDto
import com.lmuro.boqez.data.remote.dto.requests.ChangeGameTypeRequestDto
import com.lmuro.boqez.data.remote.dto.requests.ChangeTeamRequestDto
import com.lmuro.boqez.data.remote.dto.requests.LobbyCreateRequestDto
import com.lmuro.boqez.data.remote.dto.requests.LobbyWrapperRequestDto
import com.lmuro.boqez.data.remote.dto.requests.ReadyStatusRequestDto
import com.lmuro.boqez.data.remote.dto.requests.RegisterRequestDto
import com.lmuro.boqez.data.remote.dto.response.AuthResponseDto
import com.lmuro.boqez.data.remote.dto.response.IdWrapperResponse
import com.lmuro.boqez.data.remote.dto.response.LobbyJoinResponseDto
import com.lmuro.boqez.data.remote.dto.response.UserInfoResponseDto
import io.ktor.client.HttpClient

class ApiService(
    client: HttpClient
) : KtorApiService(client) {

    suspend fun login(body: AuthRequestDto): Resource<DataResponse<AuthResponseDto>, NetworkError, String?> {
        return post(
            path = "/api/auth/login",
            body = body
        )
    }

    suspend fun register(body: RegisterRequestDto): Resource<DataResponse<AuthResponseDto>, NetworkError, String?> {
        return post(
            path = "/api/auth/register",
            body = body
        )
    }

    suspend fun getUser(): Resource<DataResponse<UserInfoResponseDto>, NetworkError, String?> {
        return get("/api/user")
    }

    suspend fun createLobby(body: LobbyCreateRequestDto): Resource<DataResponse<IdWrapperResponse>, NetworkError, String?> {
        return post(
            path = "/api/lobby/create",
            body = body
        )
    }

    suspend fun joinLobby(body: LobbyWrapperRequestDto): Resource<DataResponse<LobbyJoinResponseDto>, NetworkError, String?> {
        return post(
            path = "/api/lobby/join",
            body = body
        )
    }

    suspend fun leaveTeam(body: LobbyWrapperRequestDto): Resource<Any, NetworkError, String?> {
        return post(
            path = "/api/lobby/leave-team",
            body = body
        )
    }

    suspend fun changeTeam(body: ChangeTeamRequestDto): Resource<Any, NetworkError, String?> {
        return post(
            path = "/api/lobby/join-team",
            body = body
        )
    }

    suspend fun changeReady(body: ReadyStatusRequestDto): Resource<Any, NetworkError, String?> {
        return post(
            path = "/api/lobby/ready",
            body = body
        )
    }

    suspend fun leaveLobby(body : LobbyWrapperRequestDto) : Resource<Any, NetworkError, String?> {
        return post(
            path = "/api/lobby/leave",
            body = body
        )
    }

    suspend fun changeGameType(body : ChangeGameTypeRequestDto) : Resource<Any, NetworkError,String?>{
        return post(
            "/api/lobby/change-game-type",
            body = body
        )
    }

    suspend fun startGame(body : LobbyWrapperRequestDto) : Resource<Any, NetworkError, String?>{
        return post(
            "/api/game/create",
            body = body
        )
    }
}