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
    // ── Inter (existing) ────────────────────────────────────────────────────
    val interRegular12: TextStyle,
    val interRegular14: TextStyle,
    val interRegular16: TextStyle,
    val interRegular18: TextStyle,
    val interRegular20: TextStyle,
    val interMedium12: TextStyle,
    val interMedium14: TextStyle,
    val interMedium16: TextStyle,
    val interMedium18: TextStyle,
    val interMedium20: TextStyle,
    val interSemiBold12: TextStyle,
    val interSemiBold14: TextStyle,
    val interSemiBold16: TextStyle,
    val interSemiBold18: TextStyle,
    val interSemiBold20: TextStyle,
    val interBold12: TextStyle,
    val interBold14: TextStyle,
    val interBold16: TextStyle,
    val interBold18: TextStyle,
    val interBold20: TextStyle,
    val interBold24: TextStyle,
    val interBold32: TextStyle,
    val interBold48: TextStyle,
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
    val garamondRegular14: TextStyle,
    val garamondRegular16: TextStyle,
    val garamondRegular18: TextStyle,
    val garamondItalic14: TextStyle,
    val garamondItalic16: TextStyle,
    val garamondMedium14: TextStyle,
    val garamondMedium16: TextStyle,
    val garamondSemiBold14: TextStyle,
    val garamondSemiBold16: TextStyle,
)

val LocalBoqezTypography = staticCompositionLocalOf {
    BoqezTypography(
        interRegular12 = TextStyle.Default,
        interRegular14 = TextStyle.Default,
        interRegular16 = TextStyle.Default,
        interRegular18 = TextStyle.Default,
        interRegular20 = TextStyle.Default,
        interMedium12 = TextStyle.Default,
        interMedium14 = TextStyle.Default,
        interMedium16 = TextStyle.Default,
        interMedium18 = TextStyle.Default,
        interMedium20 = TextStyle.Default,
        interSemiBold12 = TextStyle.Default,
        interSemiBold14 = TextStyle.Default,
        interSemiBold16 = TextStyle.Default,
        interSemiBold18 = TextStyle.Default,
        interSemiBold20 = TextStyle.Default,
        interBold12 = TextStyle.Default,
        interBold14 = TextStyle.Default,
        interBold16 = TextStyle.Default,
        interBold18 = TextStyle.Default,
        interBold20 = TextStyle.Default,
        interBold24 = TextStyle.Default,
        interBold32 = TextStyle.Default,
        interBold48 = TextStyle.Default,
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
    )
}

@Composable
fun provideBoqezTypography(): BoqezTypography {
    val inter = InterFontFamily()
    val cinzel = CinzelFontFamily()
    val garamond = EBGaramondFontFamily()

    return BoqezTypography(
        // ── Inter ────────────────────────────────────────────────────────────
        interRegular12 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Normal, fontSize = 12.sp),
        interRegular14 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Normal, fontSize = 14.sp),
        interRegular16 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Normal, fontSize = 16.sp),
        interRegular18 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Normal, fontSize = 18.sp),
        interRegular20 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Normal, fontSize = 20.sp),
        interMedium12 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Medium, fontSize = 12.sp),
        interMedium14 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Medium, fontSize = 14.sp),
        interMedium16 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Medium, fontSize = 16.sp),
        interMedium18 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Medium, fontSize = 18.sp),
        interMedium20 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Medium, fontSize = 20.sp),
        interSemiBold12 = TextStyle(fontFamily = inter, fontWeight = FontWeight.SemiBold, fontSize = 12.sp),
        interSemiBold14 = TextStyle(fontFamily = inter, fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
        interSemiBold16 = TextStyle(fontFamily = inter, fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
        interSemiBold18 = TextStyle(fontFamily = inter, fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
        interSemiBold20 = TextStyle(fontFamily = inter, fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
        interBold12 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Bold, fontSize = 12.sp),
        interBold14 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Bold, fontSize = 14.sp),
        interBold16 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Bold, fontSize = 16.sp),
        interBold18 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Bold, fontSize = 18.sp),
        interBold20 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Bold, fontSize = 20.sp),
        interBold24 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Bold, fontSize = 24.sp),
        interBold32 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Bold, fontSize = 32.sp),
        interBold48 = TextStyle(fontFamily = inter, fontWeight = FontWeight.Bold, fontSize = 48.sp),
        // ── Cinzel ───────────────────────────────────────────────────────────
        cinzelRegular14 = TextStyle(fontFamily = cinzel, fontWeight = FontWeight.Normal, fontSize = 14.sp, letterSpacing = 1.sp),
        cinzelRegular16 = TextStyle(fontFamily = cinzel, fontWeight = FontWeight.Normal, fontSize = 16.sp, letterSpacing = 1.sp),
        cinzelSemiBold12 = TextStyle(fontFamily = cinzel, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, letterSpacing = 1.5.sp),
        cinzelSemiBold14 = TextStyle(fontFamily = cinzel, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, letterSpacing = 1.5.sp),
        cinzelSemiBold16 = TextStyle(fontFamily = cinzel, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, letterSpacing = 1.5.sp),
        cinzelBold14 = TextStyle(fontFamily = cinzel, fontWeight = FontWeight.Bold, fontSize = 14.sp, letterSpacing = 2.sp),
        cinzelBold16 = TextStyle(fontFamily = cinzel, fontWeight = FontWeight.Bold, fontSize = 16.sp, letterSpacing = 2.sp),
        cinzelBold20 = TextStyle(fontFamily = cinzel, fontWeight = FontWeight.Bold, fontSize = 20.sp, letterSpacing = 2.sp),
        cinzelBold24 = TextStyle(fontFamily = cinzel, fontWeight = FontWeight.Bold, fontSize = 24.sp, letterSpacing = 2.sp),
        cinzelBold32 = TextStyle(fontFamily = cinzel, fontWeight = FontWeight.Bold, fontSize = 32.sp, letterSpacing = 3.sp),
        cinzelBlack40 = TextStyle(fontFamily = cinzel, fontWeight = FontWeight.Black, fontSize = 40.sp, letterSpacing = 3.sp),
        cinzelBlack48 = TextStyle(fontFamily = cinzel, fontWeight = FontWeight.Black, fontSize = 48.sp, letterSpacing = 4.sp),
        // ── EB Garamond ──────────────────────────────────────────────────────
        garamondRegular14 = TextStyle(fontFamily = garamond, fontWeight = FontWeight.Normal, fontSize = 14.sp),
        garamondRegular16 = TextStyle(fontFamily = garamond, fontWeight = FontWeight.Normal, fontSize = 16.sp),
        garamondRegular18 = TextStyle(fontFamily = garamond, fontWeight = FontWeight.Normal, fontSize = 18.sp),
        garamondItalic14 = TextStyle(fontFamily = garamond, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic, fontSize = 14.sp),
        garamondItalic16 = TextStyle(fontFamily = garamond, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic, fontSize = 16.sp),
        garamondMedium14 = TextStyle(fontFamily = garamond, fontWeight = FontWeight.Medium, fontSize = 14.sp),
        garamondMedium16 = TextStyle(fontFamily = garamond, fontWeight = FontWeight.Medium, fontSize = 16.sp),
        garamondSemiBold14 = TextStyle(fontFamily = garamond, fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
        garamondSemiBold16 = TextStyle(fontFamily = garamond, fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
    )
}