package com.lmuro.boqez.domain.model

import org.jetbrains.compose.resources.StringResource

data class Language(
    val tag: String,
    val displayName: StringResource,
    val flagCode : String
)
