package com.lmuro.boqez.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Player {
    abstract val playerId: String
    abstract val username: String
    abstract val seatIndex : Int
}

@Serializable
data class LocalPlayer(
    override val playerId: String,
    override val username: String,
    override val seatIndex: Int,
    val hand: List<Card>
) : Player()

@Serializable
data class OpponentPlayer(
    override val playerId: String,
    override val username: String,
    override val seatIndex: Int,
    val handSize: Int
) : Player()
