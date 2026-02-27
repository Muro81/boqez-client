package com.lmuro.boqez.data.remote.services

import com.lmuro.boqez.core.networking.NetworkError
import com.lmuro.boqez.core.networking.Resource
import com.lmuro.boqez.data.remote.dto.DataResponse
import com.lmuro.boqez.data.remote.dto.requests.AuthRequestDto
import com.lmuro.boqez.data.remote.dto.response.AuthResponseDto
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
}