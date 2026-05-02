package com.lmuro.boqez.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────────────────────────────────────
// Boqez palette — source of truth for all colors used in the app.
//
// Intentionally small: parchment surfaces, felt-green accents, crimson primary,
// gold ornament, warm-ink text — plus a single grey for disabled states and a
// single green for "ready" pips. Nothing else.
// ─────────────────────────────────────────────────────────────────────────────

// ── Parchment · default surface ─────────────────────────────────────────────
val Parchment       = Color(0xFFF5EAD6)  // app background
val ParchmentMid    = Color(0xFFEDE0C4)  // page chrome / room-code chip fill
val ParchmentDark   = Color(0xFFE4D3A8)  // soft borders

// ── Felt · dark green table ─────────────────────────────────────────────────
val FeltDarkest     = Color(0xFF0E2218)  // near-black felt — text on parchment
val FeltDark        = Color(0xFF1A3A2A)  // secondary button / "me" seat
val FeltLight       = Color(0xFF244D38)  // SPADA team header / language pill

// ── Crimson · primary action / KUPA team / brand mark ───────────────────────
val CrimsonBase     = Color(0xFF8B1A1A)  // primary button, title
val CrimsonLight    = Color(0xFFB52424)  // error border, hover

// ── Gold · ornament / borders / labels ──────────────────────────────────────
val GoldLight       = Color(0xFFE8B84B)  // button text on dark, inscription accents
val GoldBase        = Color(0xFFC9962A)  // focused input border
val GoldDark        = Color(0xFF8C6318)  // container borders, caps labels, links

// ── Ink-warm · text on parchment ────────────────────────────────────────────
val InkWarm         = Color(0xFF3D2E12)  // body
val InkWarmDim      = Color(0xFF7A6040)  // secondary / italic hints

// ── State greys (disabled button only) ──────────────────────────────────────
val SkyBase         = Color(0xFFCDCFD0)  // disabled button container
val InkLighter      = Color(0xFF72777A)  // disabled button label

// ── Success (ready pip) ─────────────────────────────────────────────────────
val GreenBase       = Color(0xFF23C16B)
val GreenLightest   = Color(0xFFECFCE5)

// ── White ───────────────────────────────────────────────────────────────────
val White           = Color(0xFFFFFFFF)


@Immutable
data class BoqezColors(
    // Parchment
    val parchment: Color,
    val parchmentMid: Color,
    val parchmentDark: Color,
    // Felt
    val feltDarkest: Color,
    val feltDark: Color,
    val feltLight: Color,
    // Crimson
    val crimsonBase: Color,
    val crimsonLight: Color,
    // Gold
    val goldLight: Color,
    val goldBase: Color,
    val goldDark: Color,
    // Ink-warm
    val inkWarm: Color,
    val inkWarmDim: Color,
    // Disabled-state greys
    val skyBase: Color,
    val inkLighter: Color,
    // Success
    val greenBase: Color,
    val greenLightest: Color,
    // Neutral
    val white: Color,
)

val LocalBoqezColors = staticCompositionLocalOf {
    BoqezColors(
        parchment       = Color.Unspecified,
        parchmentMid    = Color.Unspecified,
        parchmentDark   = Color.Unspecified,
        feltDarkest     = Color.Unspecified,
        feltDark        = Color.Unspecified,
        feltLight       = Color.Unspecified,
        crimsonBase     = Color.Unspecified,
        crimsonLight    = Color.Unspecified,
        goldLight       = Color.Unspecified,
        goldBase        = Color.Unspecified,
        goldDark        = Color.Unspecified,
        inkWarm         = Color.Unspecified,
        inkWarmDim      = Color.Unspecified,
        skyBase         = Color.Unspecified,
        inkLighter      = Color.Unspecified,
        greenBase       = Color.Unspecified,
        greenLightest   = Color.Unspecified,
        white           = Color.Unspecified,
    )
}

val boqezColors = BoqezColors(
    parchment       = Parchment,
    parchmentMid    = ParchmentMid,
    parchmentDark   = ParchmentDark,
    feltDarkest     = FeltDarkest,
    feltDark        = FeltDark,
    feltLight       = FeltLight,
    crimsonBase     = CrimsonBase,
    crimsonLight    = CrimsonLight,
    goldLight       = GoldLight,
    goldBase        = GoldBase,
    goldDark        = GoldDark,
    inkWarm         = InkWarm,
    inkWarmDim      = InkWarmDim,
    skyBase         = SkyBase,
    inkLighter      = InkLighter,
    greenBase       = GreenBase,
    greenLightest   = GreenLightest,
    white           = White,
)