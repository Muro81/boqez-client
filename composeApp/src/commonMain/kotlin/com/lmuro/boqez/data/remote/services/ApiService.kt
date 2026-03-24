package com.lmuro.boqez.data.remote.services

import com.lmuro.boqez.core.networking.NetworkError
import com.lmuro.boqez.core.networking.Resource
import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.data.remote.dto.DataResponse
import com.lmuro.boqez.data.remote.dto.requests.AuthRequestDto
import com.lmuro.boqez.data.remote.dto.requests.LobbyCreateRequestDto
import com.lmuro.boqez.data.remote.dto.requests.LobbyWrapperRequestDto
import com.lmuro.boqez.data.remote.dto.requests.RegisterRequestDto
import com.lmuro.boqez.data.remote.dto.response.AuthResponseDto
import com.lmuro.boqez.data.remote.dto.response.IdWrapperResponse
import com.lmuro.boqez.data.remote.dto.response.LobbyJoinResponseDto
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

    suspend fun register(body : RegisterRequestDto) : Resource<DataResponse<AuthResponseDto>, NetworkError, String?> {
        return post(
            path = "/api/auth/register",
            body = body
        )
    }

    suspend fun getUser() : Resource<Any, NetworkError, String?>{ //TODO needs work
        return get("/api/user")
    }

    suspend fun createLobby(body : LobbyCreateRequestDto) : Resource<DataResponse<IdWrapperResponse>, NetworkError, String?>{
        return post(
            path = "/api/lobby/create",
            body = body
        )
    }

    suspend fun joinLobby(body : LobbyWrapperRequestDto) : Resource<DataResponse<LobbyJoinResponseDto>, NetworkError, String?>{
        return post(
            path = "/api/lobby/join",
            body = body
        )
    }
}