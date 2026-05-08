package com.lmuro.boqez.data.remote.services

import com.lmuro.boqez.BuildKonfig
import com.lmuro.boqez.data.remote.dto.socket.WebSocketMessage
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class WSService(
    private val client: HttpClient
){
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var session : WebSocketSession? = null
    private val _messages = MutableSharedFlow<WebSocketMessage>()
    val messages : SharedFlow<WebSocketMessage> = _messages.asSharedFlow()


    fun connect(userId: String) {
        scope.launch {
            client.webSocket("${BuildKonfig.WS_URL}/ws?userId=$userId") {
                session = this
                Napier.d("WebSocket connected!", tag = "WSService")
                try {
                    for (frame in incoming) {
                        when (frame) {
                            is Frame.Text -> {
                                val text = frame.readText()
                                Napier.d("Raw WS frame: $text", tag = "WSService")
                                val message = Json.decodeFromString<WebSocketMessage>(text)
                                _messages.emit(message)
                            }
                            is Frame.Close -> break
                            else -> Unit
                        }
                    }
                } catch (e: Exception) {
                    Napier.e("WebSocket error: ${e.message}", tag = "WSService")
                } finally {
                    session = null
                }
            }
        }
    }

    suspend fun disconnect() {
        session?.close(CloseReason(CloseReason.Codes.NORMAL, "Client disconnecting"))
        session = null
    }

    suspend fun sendMessage(message: WebSocketMessage) {
        session?.send(Frame.Text(Json.encodeToString(WebSocketMessage.serializer(), message)))
    }

}