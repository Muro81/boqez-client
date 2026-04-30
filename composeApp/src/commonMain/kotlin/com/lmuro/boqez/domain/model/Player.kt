package com.lmuro.boqez.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Player {
    abstract val playerId: String
    abstract val username: String
}

@Serializable
@SerialName("local")
data class LocalPlayer(
    override val playerId: String,
    override val username: String,
    val hand: List<Card>
) : Player()

@Serializable
@SerialName("opponent")
data class OpponentPlayer(
    override val playerId: String,
    override val username: String,
    val handSize: Int
) : Player()
