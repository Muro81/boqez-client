package com.lmuro.boqez.data.repository

import com.lmuro.boqez.core.networking.NetworkError
import com.lmuro.boqez.core.networking.Resource
import com.lmuro.boqez.core.networking.map
import com.lmuro.boqez.data.remote.dto.requests.AuthRequestDto
import com.lmuro.boqez.data.remote.dto.requests.RegisterRequestDto
import com.lmuro.boqez.data.remote.mappers.toAuth
import com.lmuro.boqez.data.remote.services.ApiService
import com.lmuro.boqez.domain.model.Auth
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

    override suspend fun getUser(): Resource<Any, NetworkError, String?> {
        return apiService.getUser()
    }
}