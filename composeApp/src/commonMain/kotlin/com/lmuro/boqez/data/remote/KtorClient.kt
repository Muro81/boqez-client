package com.lmuro.boqez.data.remote

import com.lmuro.boqez.BuildKonfig
import com.lmuro.boqez.core.navigation.Screen
import com.lmuro.boqez.core.navigation.utils.Navigator
import com.lmuro.boqez.data.local.DataStoreApi
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.ACCESS_TOKEN
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.DEVICE_ID
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.DEVICE_NAME
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.REFRESH_TOKEN
import com.lmuro.boqez.data.local.DataStorePreferenceKeys.Companion.USER_PREFERRED_LANGUAGE
import com.lmuro.boqez.data.remote.dto.DataResponse
import com.lmuro.boqez.data.remote.dto.requests.RefreshTokenRequestDto
import com.lmuro.boqez.data.remote.dto.response.AuthResponseDto
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

fun provideKtorClient(
    dataStoreApi: DataStoreApi,
    navigator: Navigator
): HttpClient {
    val languageKey = runBlocking { dataStoreApi.read(USER_PREFERRED_LANGUAGE).orEmpty() }
    val client = HttpClient {
        engine {
            dispatcher = Dispatchers.IO
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
            socketTimeoutMillis = 30_000
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 3)
            exponentialDelay()
        }
        defaultRequest {
            url(BuildKonfig.BASE_URL) // Your base URL
            // You can also set default headers here
            header("Content-Type", "application/json")
            header("Accept-Language",languageKey)
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(message, null, "HTTP Client")
                }
            }
            level = LogLevel.ALL // equivalent to BODY level
        }
        install(Auth) {
            bearer {
                cacheTokens = false
                loadTokens {
                    // Load your tokens here
                    val accessToken = dataStoreApi.read(ACCESS_TOKEN).orEmpty()
                    val refreshToken = dataStoreApi.read(REFRESH_TOKEN)
                    Napier.v("Signed with $accessToken $refreshToken")
                    BearerTokens(
                        accessToken = accessToken,
                        refreshToken = refreshToken
                    )
                }

                refreshTokens {
                    try {
                        val deviceName = dataStoreApi.read(DEVICE_NAME).orEmpty()
                        val deviceId = dataStoreApi.read(DEVICE_ID).orEmpty()
                        val newTokens = client.post {
                            markAsRefreshTokenRequest()
                            url("/api/auth/refresh")
                            setBody(
                                RefreshTokenRequestDto(
                                    token = dataStoreApi.read(REFRESH_TOKEN).orEmpty(),
                                    device = deviceName + "_" + deviceId
                                )
                            )
                        }.body<DataResponse<AuthResponseDto>>().data

                        // Save new tokens if needed
                        dataStoreApi.update(ACCESS_TOKEN, newTokens.accessToken)
                        dataStoreApi.update(REFRESH_TOKEN, newTokens.refreshToken)

                        BearerTokens(
                            accessToken = newTokens.accessToken,
                            refreshToken = newTokens.refreshToken
                        )
                    } catch (e: Exception) {

                        dataStoreApi.delete(ACCESS_TOKEN)
                        dataStoreApi.delete(REFRESH_TOKEN)
                        navigator.navigateTo(
                            destination = Screen.LoginScreen
                        ){
                            popUpTo(Screen.ROOT)
                        }

                        null
                    }
                }
            }
        }
    }

    return client
}