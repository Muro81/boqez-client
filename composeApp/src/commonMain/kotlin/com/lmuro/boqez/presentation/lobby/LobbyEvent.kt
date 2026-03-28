package com.lmuro.boqez.presentation.lobby

import com.lmuro.boqez.core.utils.GameType
import com.lmuro.boqez.presentation.base.BaseEvent

sealed class LobbyEvent : BaseEvent {
    data class OnTeamChange(val teamId: Int?) : LobbyEvent()
    data object OnLeaveGame : LobbyEvent()
    data object OnReadyChange : LobbyEvent()
    data object OnStartGame : LobbyEvent()
    data class OnKickPlayer(val playerId: String) : LobbyEvent()
    data object OnKickConfirm : LobbyEvent()
    data object OnKickCancel : LobbyEvent()
    data class OnChangeGameType(val gameType: GameType) : LobbyEvent()
}