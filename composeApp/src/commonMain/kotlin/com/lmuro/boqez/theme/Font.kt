package com.lmuro.boqez.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.cinzel_black
import boqez.composeapp.generated.resources.cinzel_bold
import boqez.composeapp.generated.resources.cinzel_regular
import boqez.composeapp.generated.resources.cinzel_semi_bold
import boqez.composeapp.generated.resources.eb_garamond_medium
import boqez.composeapp.generated.resources.eb_garamond_regular
import boqez.composeapp.generated.resources.eb_garamond_regular_italic
import boqez.composeapp.generated.resources.eb_garamond_semibold
import org.jetbrains.compose.resources.Font

// Display font — headings, titles, button labels, team names, field labels
@Composable
fun CinzelFontFamily() = FontFamily(
    Font(Res.font.cinzel_regular, weight = FontWeight.Normal),
    Font(Res.font.cinzel_semi_bold, weight = FontWeight.SemiBold),
    Font(Res.font.cinzel_bold, weight = FontWeight.Bold),
    Font(Res.font.cinzel_black, weight = FontWeight.Black),
)

// Body font — player names, descriptions, placeholders, italic text
@Composable
fun EBGaramondFontFamily() = FontFamily(
    Font(Res.font.eb_garamond_regular, weight = FontWeight.Normal),
    Font(Res.font.eb_garamond_regular_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(Res.font.eb_garamond_medium, weight = FontWeight.Medium),
    Font(Res.font.eb_garamond_semibold, weight = FontWeight.SemiBold),
)