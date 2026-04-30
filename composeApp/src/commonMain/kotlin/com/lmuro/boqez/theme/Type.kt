package com.lmuro.boqez.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class BoqezTypography(
    // ── Cinzel (display / headings) ─────────────────────────────────────────
    val cinzelRegular14: TextStyle,
    val cinzelRegular16: TextStyle,
    val cinzelSemiBold12: TextStyle,
    val cinzelSemiBold14: TextStyle,
    val cinzelSemiBold16: TextStyle,
    val cinzelBold14: TextStyle,
    val cinzelBold16: TextStyle,
    val cinzelBold20: TextStyle,
    val cinzelBold24: TextStyle,
    val cinzelBold32: TextStyle,
    val cinzelBlack40: TextStyle,
    val cinzelBlack48: TextStyle,
    // ── EB Garamond (body / italic) ─────────────────────────────────────────
    val garamondRegular10: TextStyle,
    val garamondRegular14: TextStyle,
    val garamondRegular16: TextStyle,
    val garamondRegular18: TextStyle,
    val garamondItalic12: TextStyle,
    val garamondItalic14: TextStyle,
    val garamondItalic16: TextStyle,
    val garamondMedium14: TextStyle,
    val garamondMedium16: TextStyle,
    val garamondSemiBold14: TextStyle,
    val garamondSemiBold16: TextStyle,
    val garamondSemiBold18: TextStyle,
)

val LocalBoqezTypography = staticCompositionLocalOf {
    BoqezTypography(
        cinzelRegular14 = TextStyle.Default,
        cinzelRegular16 = TextStyle.Default,
        cinzelSemiBold12 = TextStyle.Default,
        cinzelSemiBold14 = TextStyle.Default,
        cinzelSemiBold16 = TextStyle.Default,
        cinzelBold14 = TextStyle.Default,
        cinzelBold16 = TextStyle.Default,
        cinzelBold20 = TextStyle.Default,
        cinzelBold24 = TextStyle.Default,
        cinzelBold32 = TextStyle.Default,
        cinzelBlack40 = TextStyle.Default,
        cinzelBlack48 = TextStyle.Default,
        garamondRegular14 = TextStyle.Default,
        garamondRegular16 = TextStyle.Default,
        garamondRegular18 = TextStyle.Default,
        garamondItalic14 = TextStyle.Default,
        garamondItalic16 = TextStyle.Default,
        garamondMedium14 = TextStyle.Default,
        garamondMedium16 = TextStyle.Default,
        garamondSemiBold14 = TextStyle.Default,
        garamondSemiBold16 = TextStyle.Default,
        garamondItalic12 = TextStyle.Default,
        garamondRegular10 = TextStyle.Default,
        garamondSemiBold18 = TextStyle.Default
    )
}

@Composable
fun provideBoqezTypography(): BoqezTypography {
    val cinzel = CinzelFontFamily()
    val garamond = EBGaramondFontFamily()

    return BoqezTypography(
        // ── Cinzel ───────────────────────────────────────────────────────────
        cinzelRegular14 = TextStyle(
            fontFamily = cinzel,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = 1.sp
        ),
        cinzelRegular16 = TextStyle(
            fontFamily = cinzel,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 1.sp
        ),
        cinzelSemiBold12 = TextStyle(
            fontFamily = cinzel,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            letterSpacing = 1.5.sp
        ),
        cinzelSemiBold14 = TextStyle(
            fontFamily = cinzel,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            letterSpacing = 1.5.sp
        ),
        cinzelSemiBold16 = TextStyle(
            fontFamily = cinzel,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = 1.5.sp
        ),
        cinzelBold14 = TextStyle(
            fontFamily = cinzel,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            letterSpacing = 2.sp
        ),
        cinzelBold16 = TextStyle(
            fontFamily = cinzel,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            letterSpacing = 2.sp
        ),
        cinzelBold20 = TextStyle(
            fontFamily = cinzel,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            letterSpacing = 2.sp
        ),
        cinzelBold24 = TextStyle(
            fontFamily = cinzel,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            letterSpacing = 2.sp
        ),
        cinzelBold32 = TextStyle(
            fontFamily = cinzel,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            letterSpacing = 3.sp
        ),
        cinzelBlack40 = TextStyle(
            fontFamily = cinzel,
            fontWeight = FontWeight.Black,
            fontSize = 40.sp,
            letterSpacing = 3.sp
        ),
        cinzelBlack48 = TextStyle(
            fontFamily = cinzel,
            fontWeight = FontWeight.Black,
            fontSize = 48.sp,
            letterSpacing = 4.sp
        ),
        // ── EB Garamond ──────────────────────────────────────────────────────
        garamondItalic12 = TextStyle(
            fontFamily = garamond,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic
        ),
        garamondRegular14 = TextStyle(
            fontFamily = garamond,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        garamondRegular16 = TextStyle(
            fontFamily = garamond,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        garamondRegular18 = TextStyle(
            fontFamily = garamond,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        ),
        garamondItalic14 = TextStyle(
            fontFamily = garamond,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            fontSize = 14.sp
        ),
        garamondItalic16 = TextStyle(
            fontFamily = garamond,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp
        ),
        garamondMedium14 = TextStyle(
            fontFamily = garamond,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),
        garamondMedium16 = TextStyle(
            fontFamily = garamond,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        garamondSemiBold14 = TextStyle(
            fontFamily = garamond,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        ),
        garamondSemiBold16 = TextStyle(
            fontFamily = garamond,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        ),
        garamondRegular10 = TextStyle(
            fontFamily = garamond,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        ),
        garamondSemiBold18 = TextStyle(
            fontFamily = garamond,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
    )
}