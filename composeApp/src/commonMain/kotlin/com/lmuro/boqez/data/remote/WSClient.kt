package com.lmuro.boqez.data.remote

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.pingInterval
import kotlin.time.Duration.Companion.seconds

fun provideWsClient() : HttpClient {
    return HttpClient {
        install(WebSockets) {
            pingInterval = 15.seconds
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(message, null, "WS Client")
                }
            }
            level = LogLevel.ALL
        }
    }
}