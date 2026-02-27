package com.lmuro.boqez.domain.repository

import com.lmuro.boqez.core.networking.NetworkError
import com.lmuro.boqez.core.networking.Resource
import com.lmuro.boqez.domain.model.Auth

interface BoqezRepository {
    suspend fun login(email : String, password : String, device : String) : Resource<Auth, NetworkError, String?>
}