package com.lmuro.boqez.domain.model

data class LobbyUser(
    val userId : String,
    val username : String,
    val isReady : Boolean,
    val teamId : Int?
)
