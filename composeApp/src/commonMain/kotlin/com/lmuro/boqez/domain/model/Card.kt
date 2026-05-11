package com.lmuro.boqez.domain.model

import com.lmuro.boqez.core.utils.Rank
import com.lmuro.boqez.core.utils.Suit
import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val suit: Suit,
    val rank: Rank
) {
    companion object {
        val tresetaComparator = compareBy<Card>(
            { it.suit.sortOrder },
            { it.rank.tresetaSortOrder }
        )
    }
}




