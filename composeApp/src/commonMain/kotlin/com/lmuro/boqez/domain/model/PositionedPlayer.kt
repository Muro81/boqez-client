package com.lmuro.boqez.domain.model

import com.lmuro.boqez.core.utils.TablePosition

data class PositionedPlayer(
    val player: Player,
    val position: TablePosition
)