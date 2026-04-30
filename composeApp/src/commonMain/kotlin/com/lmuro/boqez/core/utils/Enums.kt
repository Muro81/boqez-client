package com.lmuro.boqez.core.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
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


@Serializable(with = SuitSerializer::class)
enum class Suit {
    KUPA, DINAR, BASTON, SPADA
}

object SuitSerializer : KSerializer<Suit> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Suit", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Suit) {
        val code = when (value) {
            Suit.KUPA -> "K"
            Suit.DINAR -> "D"
            Suit.BASTON -> "B"
            Suit.SPADA -> "S"
        }
        encoder.encodeString(code)
    }

    override fun deserialize(decoder: Decoder): Suit {
        return when (val code = decoder.decodeString()) {
            "K" -> Suit.KUPA
            "D" -> Suit.DINAR
            "B" -> Suit.BASTON
            "S" -> Suit.SPADA
            else -> throw SerializationException("Unknown suit code: $code")
        }
    }
}

@Serializable(with = RankSerializer::class)
enum class Rank {
    ACE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    JACK,
    KNIGHT,
    KING
}

object RankSerializer : KSerializer<Rank> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Rank", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Rank) {
        val code = when (value) {
            Rank.ACE -> "A"
            Rank.TWO -> "2"
            Rank.THREE -> "3"
            Rank.FOUR -> "4"
            Rank.FIVE -> "5"
            Rank.SIX -> "6"
            Rank.SEVEN -> "7"
            Rank.JACK -> "J"
            Rank.KNIGHT -> "N"
            Rank.KING -> "K"
        }
        encoder.encodeString(code)
    }

    override fun deserialize(decoder: Decoder): Rank {
        return when (val code = decoder.decodeString()) {
            "A" -> Rank.ACE
            "2" -> Rank.TWO
            "3" -> Rank.THREE
            "4" -> Rank.FOUR
            "5" -> Rank.FIVE
            "6" -> Rank.SIX
            "7" -> Rank.SEVEN
            "J" -> Rank.JACK
            "N" -> Rank.KNIGHT
            "K" -> Rank.KING
            else -> throw SerializationException("Unknown rank code: $code")
        }
    }
}



enum class WebSocketMessageType(val message : String){
    //LOBBY MESSAGES
    PLAYER_JOINED("player_joined"),
    PLAYER_LEFT("player_left"),
    OWNER_LEFT("owner_left"),
    OWNER_CHANGED("owner_changed"),
    PLAYER_JOINED_TEAM("player_joined_team"),
    PLAYER_LEFT_TEAM("player_left_team"),
    PLAYER_READY("player_ready"),
    GAME_TYPE_CHANGE("game_type_change"),
    KICK_PLAYER("kick_player"),

    //GAME MESSAGES
    START_GAME("start_game"),
    FINISH_GAME("finish_game"),
    START_ROUND("start_round"),
    FINISH_ROUND("finish_round"),
    PLAY_CARD("play_card"),
    FINISH_TRICK("finish_trick"),
    CALL_POINTS("call_points"),
    DRAW_CARD("draw_card"),
    SWAP_CARD("swap_card"),
    PLAYER_DISCONNECTED("player_disconnect"),
    GESTURE("gesture")
}