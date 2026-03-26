package com.lmuro.boqez.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LobbyUser(
    val userId : String,
    val username : String,
    val isReady : Boolean,
    val teamId : Int?
)
