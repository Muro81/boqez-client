package com.lmuro.boqez.data.remote.mappers

import com.lmuro.boqez.data.remote.dto.response.UserInfoResponseDto
import com.lmuro.boqez.domain.model.UserInfo

fun UserInfoResponseDto.toUserInfo() : UserInfo {
    return UserInfo(
        username = username,
        userId = userId
    )
}