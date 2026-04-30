package com.lmuro.boqez.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val teamId : Int,
    val players : List<Player>
)
