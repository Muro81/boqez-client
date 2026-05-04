package com.lmuro.boqez.presentation.game

import com.lmuro.boqez.core.utils.Gesture
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.presentation.base.BaseEvent

sealed class GameEvent : BaseEvent {
    data class OnPlayCard(val card: Card) : GameEvent()
    data class OnGesture(val gesture : Gesture) : GameEvent()
    data object OnLeaveGame : GameEvent()
    data class OnCallCards(val calledCards : List<Card>) : GameEvent()
    data class OnSwapCards(val cardsToSwap : List<Card>) : GameEvent()
}