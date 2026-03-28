package com.lmuro.boqez.theme


import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


val InkLighter = Color(0xFF72777A)
val InkLight = Color(0xFF6C7072)
val InkBase = Color(0xFF404446)
val InkDark = Color(0xFF303437)
val InkDarker = Color(0xFF202325)
val InkDarkest = Color(0xFF090A0A)

val SkyLightest = Color(0xFFF7F9FA)
val SkyLighter = Color(0xFFF2F4F5)
val SkyLight = Color(0xFFE3E5E5)
val SkyBase = Color(0xFFCDCFD0)
val SkyDark = Color(0xFF979C9E)

val PrimaryLightest = Color(0xFFCDFEFA)
val PrimaryLighter = Color(0xFF7FE5DD)
val PrimaryLight = Color(0xFF19C1B4)
val PrimaryBase = Color(0xFF18847C)
val PrimaryDarkest = Color(0xFF0C5B55)

val RedLightest = Color(0xFFFFE5E5)
val RedLighter = Color(0xFFFF9898)
val RedLight = Color(0xFFFF6D6D)
val RedBase = Color(0xFFFF5247)
val RedDarkest = Color(0xFFD3180C)

val GreenLightest = Color(0xFFECFCE5)
val GreenLighter = Color(0xFF7DDE86)
val GreenLight = Color(0xFF4CD471)
val GreenBase = Color(0xFF23C16B)
val GreenDarkest = Color(0xFF198155)

val YellowLightest = Color(0xFFFFEFD7)
val YellowLighter = Color(0xFFFFD188)
val YellowLight = Color(0xFFFFC462)
val YellowBase = Color(0xFFFFB323)
val YellowDarkest = Color(0xFFA05E03)

val White = Color(0xFFFFFFFF)
val LabelBlack = Color(0xFF12121D)
val DarkGreen = Color(0xFF213D3B)
val BlackText = Color(0xFF2A2A2A)

val Grey100 = Color(0xFF131214)

val LabelsPrimary = Color(0xFF404040)

val GoldYellow = Color(0xFFD28900)

// ── Card Game / Lobby Theme ──────────────────────────────────────────────────

// Parchment — warm off-white card surface
val Parchment = Color(0xFFF5EAD6)
val ParchmentMid = Color(0xFFEDE0C4)
val ParchmentDark = Color(0xFFE4D3A8)

// Felt — dark green table surface
val FeltDarkest = Color(0xFF0E2218)
val FeltDark = Color(0xFF1A3A2A)
val FeltLight = Color(0xFF244D38)

// Gold — ornamental accents, borders, strips
val GoldLight = Color(0xFFE8B84B)
val GoldBase = Color(0xFFC9962A)
val GoldDark = Color(0xFF8C6318)

// Crimson — Team A, active states, primary button
val CrimsonPale = Color(0xFFF9EAEA)
val CrimsonLight = Color(0xFFB52424)
val CrimsonBase = Color(0xFF8B1A1A)

// Ink warm — player names, body text on parchment
val InkWarm = Color(0xFF3D2E12)
val InkWarmDim = Color(0xFF7A6040)


@Immutable
data class BoqezColors(
    val inkLighter: Color,
    val inkLight: Color,
    val inkBase: Color,
    val inkDark: Color,
    val inkDarker: Color,
    val inkDarkest: Color,
    val skyLightest: Color,
    val skyLighter: Color,
    val skyLight: Color,
    val skyBase: Color,
    val skyDark: Color,
    val primaryLightest: Color,
    val primaryLighter: Color,
    val primaryLight: Color,
    val primaryBase: Color,
    val primaryDarkest: Color,
    val redLightest: Color,
    val redLighter: Color,
    val redLight: Color,
    val redBase: Color,
    val redDarkest: Color,
    val greenLightest: Color,
    val greenLighter: Color,
    val greenLight: Color,
    val greenBase: Color,
    val greenDarkest: Color,
    val yellowLightest: Color,
    val yellowLighter: Color,
    val yellowLight: Color,
    val yellowBase: Color,
    val yellowDarkest: Color,
    val white: Color,
    val labelBlack: Color,
    val darkGreen: Color,
    val grey100: Color,
    val blackText: Color,
    val labelsPrimary: Color,
    val goldYellow: Color,
    // Card game
    val parchment: Color,
    val parchmentMid: Color,
    val parchmentDark: Color,
    val feltDarkest: Color,
    val feltDark: Color,
    val feltLight: Color,
    val goldLight: Color,
    val goldBase: Color,
    val goldDark: Color,
    val crimsonPale: Color,
    val crimsonLight: Color,
    val crimsonBase: Color,
    val inkWarm: Color,
    val inkWarmDim: Color,
)

val LocalBoqezColors = staticCompositionLocalOf {
    BoqezColors(
        inkLighter = Color.Unspecified,
        inkLight = Color.Unspecified,
        inkBase = Color.Unspecified,
        inkDark = Color.Unspecified,
        inkDarker = Color.Unspecified,
        inkDarkest = Color.Unspecified,
        skyLightest = Color.Unspecified,
        skyLighter = Color.Unspecified,
        skyLight = Color.Unspecified,
        skyBase = Color.Unspecified,
        skyDark = Color.Unspecified,
        primaryLightest = Color.Unspecified,
        primaryLighter = Color.Unspecified,
        primaryLight = Color.Unspecified,
        primaryBase = Color.Unspecified,
        primaryDarkest = Color.Unspecified,
        redLightest = Color.Unspecified,
        redLighter = Color.Unspecified,
        redLight = Color.Unspecified,
        redBase = Color.Unspecified,
        redDarkest = Color.Unspecified,
        greenLightest = Color.Unspecified,
        greenLighter = Color.Unspecified,
        greenLight = Color.Unspecified,
        greenBase = Color.Unspecified,
        greenDarkest = Color.Unspecified,
        yellowLightest = Color.Unspecified,
        yellowLighter = Color.Unspecified,
        yellowLight = Color.Unspecified,
        yellowBase = Color.Unspecified,
        yellowDarkest = Color.Unspecified,
        white = Color.Unspecified,
        labelBlack = Color.Unspecified,
        darkGreen = Color.Unspecified,
        grey100 = Color.Unspecified,
        blackText = Color.Unspecified,
        labelsPrimary = Color.Unspecified,
        goldYellow = Color.Unspecified,
        parchment = Color.Unspecified,
        parchmentMid = Color.Unspecified,
        parchmentDark = Color.Unspecified,
        feltDarkest = Color.Unspecified,
        feltDark = Color.Unspecified,
        feltLight = Color.Unspecified,
        goldLight = Color.Unspecified,
        goldBase = Color.Unspecified,
        goldDark = Color.Unspecified,
        crimsonPale = Color.Unspecified,
        crimsonLight = Color.Unspecified,
        crimsonBase = Color.Unspecified,
        inkWarm = Color.Unspecified,
        inkWarmDim = Color.Unspecified,
    )
}

val boqezColors = BoqezColors(
    inkLighter = InkLighter,
    inkLight = InkLight,
    inkBase = InkBase,
    inkDark = InkDark,
    inkDarker = InkDarker,
    inkDarkest = InkDarkest,
    skyLightest = SkyLightest,
    skyLighter = SkyLighter,
    skyLight = SkyLight,
    skyBase = SkyBase,
    skyDark = SkyDark,
    primaryLightest = PrimaryLightest,
    primaryLighter = PrimaryLighter,
    primaryLight = PrimaryLight,
    primaryBase = PrimaryBase,
    primaryDarkest = PrimaryDarkest,
    redLightest = RedLightest,
    redLighter = RedLighter,
    redLight = RedLight,
    redBase = RedBase,
    redDarkest = RedDarkest,
    greenLightest = GreenLightest,
    greenLighter = GreenLighter,
    greenLight = GreenLight,
    greenBase = GreenBase,
    greenDarkest = GreenDarkest,
    yellowLightest = YellowLightest,
    yellowLighter = YellowLighter,
    yellowLight = YellowLight,
    yellowBase = YellowBase,
    yellowDarkest = YellowDarkest,
    white = White,
    labelBlack = LabelBlack,
    darkGreen = DarkGreen,
    grey100 = Grey100,
    blackText = BlackText,
    labelsPrimary = LabelsPrimary,
    goldYellow = GoldYellow,
    parchment = Parchment,
    parchmentMid = ParchmentMid,
    parchmentDark = ParchmentDark,
    feltDarkest = FeltDarkest,
    feltDark = FeltDark,
    feltLight = FeltLight,
    goldLight = GoldLight,
    goldBase = GoldBase,
    goldDark = GoldDark,
    crimsonPale = CrimsonPale,
    crimsonLight = CrimsonLight,
    crimsonBase = CrimsonBase,
    inkWarm = InkWarm,
    inkWarmDim = InkWarmDim,
)
