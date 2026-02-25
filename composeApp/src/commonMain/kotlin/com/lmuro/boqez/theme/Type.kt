package com.lmuro.boqez.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class BoqezTypography(
    val interRegular12 : TextStyle,
    val interRegular14 : TextStyle,
    val interRegular16 : TextStyle,
    val interRegular18 : TextStyle,
    val interRegular20 : TextStyle,
    val interMedium12 : TextStyle,
    val interMedium14 : TextStyle,
    val interMedium16 : TextStyle,
    val interMedium18 : TextStyle,
    val interMedium20 : TextStyle,
    val interSemiBold12 : TextStyle,
    val interSemiBold14 : TextStyle,
    val interSemiBold16 : TextStyle,
    val interSemiBold18 : TextStyle,
    val interSemiBold20 : TextStyle,
    val interBold12 : TextStyle,
    val interBold14 : TextStyle,
    val interBold16 : TextStyle,
    val interBold18 : TextStyle,
    val interBold20 : TextStyle,
    val interBold24 : TextStyle,
    val interBold32 : TextStyle,
    val interBold48 : TextStyle,
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
        interBold48 = TextStyle.Default
    )
}

@Composable
fun provideBoqezTypography() : BoqezTypography {
    val interFontFamily = InterFontFamily()

    return BoqezTypography(
        interRegular12 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        interRegular14 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        interRegular16 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        interRegular18 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        ),
        interRegular20 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        ),
        interMedium12 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        interMedium14 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),
        interMedium16 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        interMedium18 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        ),
        interMedium20 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),
        interSemiBold12 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        ),
        interSemiBold14 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        ),
        interSemiBold16 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        ),
        interSemiBold18 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        ),
        interSemiBold20 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        ),
        interBold12 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        ),
        interBold14 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ),
        interBold16 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        interBold18 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        ),
        interBold20 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        interBold24 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        interBold32 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        ),
        interBold48 = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp
        )
    )
}