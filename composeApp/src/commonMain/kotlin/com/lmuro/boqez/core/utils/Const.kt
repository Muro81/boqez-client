package com.lmuro.boqez.core.utils

import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.english
import boqez.composeapp.generated.resources.montenegrin
import com.lmuro.boqez.domain.model.Language

object Const {
    val languages: List<Language> = listOf(
        Language(
            tag = "en",
            displayName = Res.string.english,
            flagCode = "GB"
        ),
        Language(
            tag = "me",
            displayName = Res.string.montenegrin,
            flagCode = "ME"
        )
    )
     const val LOBBY_CODE_LENGTH = 6
}