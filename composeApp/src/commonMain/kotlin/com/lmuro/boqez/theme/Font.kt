package com.lmuro.boqez.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.inter_bold
import boqez.composeapp.generated.resources.inter_medium
import boqez.composeapp.generated.resources.inter_regular
import boqez.composeapp.generated.resources.inter_semi_bold
import org.jetbrains.compose.resources.Font

@Composable
fun InterFontFamily() = FontFamily(
    Font(Res.font.inter_regular, weight = FontWeight.Normal),
    Font(Res.font.inter_medium, weight = FontWeight.Medium),
    Font(Res.font.inter_semi_bold, weight = FontWeight.SemiBold),
    Font(Res.font.inter_bold, weight = FontWeight.Bold)
)