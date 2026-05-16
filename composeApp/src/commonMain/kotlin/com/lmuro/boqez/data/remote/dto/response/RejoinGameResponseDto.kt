package com.lmuro.boqez.data.remote.dto.response

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.core.utils.Suit
import com.lmuro.boqez.domain.model.Card
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RejoinGameResponseDto(
    @SerialName("game_id") val gameId: String,
    @SerialName("game_type") val gameType: GameType,
    @SerialName("teams") val teams: List<RejoinTeamDto>,
    @SerialName("current_player_id") val currentPlayerId: String,
    @SerialName("deck") val deck: List<Card>,
    @SerialName("discard_pile") val discardPile: List<Card>,
    @SerialName("trump_suit") val trumpSuit: Suit?,
    @SerialName("bottom_card") val bottomCard: Card?,
    @SerialName("hand") val hand: List<Card>,
    @SerialName("dealer_peek_card") val dealerPeekCard : Card?
)