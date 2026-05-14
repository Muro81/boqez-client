package com.lmuro.boqez.domain.model

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.Suit

data class Rejoin(
    val gameId: String,
    val gameType: GameType,
    val teams: List<Team>,
    val currentPlayerId: String,
    val deck: List<Card>,
    val discardPile: List<Card>,
    val trumpSuit: Suit?,
    val bottomCard: Card?,
    val hand: List<Card>
)