package com.lmuro.boqez.core.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = GameTypeSerializer::class)
enum class GameType(val gameName : String){
    BRISKULA("briskula"),
    TRESETA("treseta"),
    TERCULJA("terculja")
}

object GameTypeSerializer : KSerializer<GameType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("GameType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: GameType) {
        encoder.encodeString(value.gameName)
    }

    override fun deserialize(decoder: Decoder): GameType {
        val gameName = decoder.decodeString()
        return GameType.entries.first { it.gameName == gameName }
    }
}

enum class WebSocketMessageType(val message : String){
    PLAYER_JOINED("player_joined"),
    PLAYER_LEFT("player_left"),
    OWNER_LEFT("owner_left"),
    OWNER_CHANGED("owner_changed"),
    PLAYER_JOINED_TEAM("player_joined_team"),
    PLAYER_LEFT_TEAM("player_left_team"),
    PLAYER_READY("player_ready"),
    GAME_TYPE_CHANGE("game_type_change"),
    KICK_PLAYER("kick_player")
}