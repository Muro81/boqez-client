package com.lmuro.boqez.domain.model

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.Suit

data class GameStartData(
    val gameType: GameType,
    val teams: List<Team>,
    val currentPlayerId: String,
    val deck: List<Card>,
    val discardPile: List<Card>,
    val trumpSuit: Suit?
)
