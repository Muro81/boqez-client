package com.lmuro.boqez.data.remote.mappers

import com.lmuro.boqez.data.remote.dto.response.AuthResponseDto
import com.lmuro.boqez.domain.model.Auth

fun AuthResponseDto.toAuth(): Auth {
    return Auth(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}