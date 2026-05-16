package com.lmuro.boqez.presentation.game

import com.lmuro.boqez.core.utils.Gesture
import com.lmuro.boqez.domain.model.Card
import com.lmuro.boqez.presentation.base.BaseEvent

sealed class GameEvent : BaseEvent {
    data class OnPlayCard(val card: Card) : GameEvent()
    data class OnGesture(val gesture : Gesture) : GameEvent()
    data object OnLeaveGame : GameEvent()
    data object OnCallCards : GameEvent()
    data class OnSwapCards(val cardsToSwap : List<Card>) : GameEvent()
    data object OnReady : GameEvent()
    data object OnLeaveGameConfirm : GameEvent()
    data object OnLeaveGameDismiss : GameEvent()
    data object OnDeckClick : GameEvent()
}