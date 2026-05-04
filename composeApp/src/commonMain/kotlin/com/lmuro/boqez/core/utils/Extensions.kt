package com.lmuro.boqez.core.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import boqez.composeapp.generated.resources.Res
import boqez.composeapp.generated.resources.error_field_required
import boqez.composeapp.generated.resources.error_invalid_email
import boqez.composeapp.generated.resources.error_min_4_char
import boqez.composeapp.generated.resources.error_password_max_length
import boqez.composeapp.generated.resources.error_password_min_lenght
import boqez.composeapp.generated.resources.error_password_no_letter
import boqez.composeapp.generated.resources.error_password_no_numbers
import boqez.composeapp.generated.resources.error_password_no_special
import boqez.composeapp.generated.resources.error_passwords_no_match
import boqez.composeapp.generated.resources.error_min_3_char
import boqez.composeapp.generated.resources.error_max_50_char
import boqez.composeapp.generated.resources.error_username_invalid_chars
import boqez.composeapp.generated.resources.img_ace_baston
import boqez.composeapp.generated.resources.img_ace_dinar
import boqez.composeapp.generated.resources.img_ace_kupa
import boqez.composeapp.generated.resources.img_ace_spada
import boqez.composeapp.generated.resources.img_five_baston
import boqez.composeapp.generated.resources.img_five_dinar
import boqez.composeapp.generated.resources.img_five_kupa
import boqez.composeapp.generated.resources.img_five_spada
import boqez.composeapp.generated.resources.img_four_baston
import boqez.composeapp.generated.resources.img_four_dinar
import boqez.composeapp.generated.resources.img_four_kupa
import boqez.composeapp.generated.resources.img_four_spada
import boqez.composeapp.generated.resources.img_jack_baston
import boqez.composeapp.generated.resources.img_jack_dinar
import boqez.composeapp.generated.resources.img_jack_kupa
import boqez.composeapp.generated.resources.img_jack_spada
import boqez.composeapp.generated.resources.img_king_baston
import boqez.composeapp.generated.resources.img_king_dinar
import boqez.composeapp.generated.resources.img_king_kupa
import boqez.composeapp.generated.resources.img_king_spada
import boqez.composeapp.generated.resources.img_knight_baston
import boqez.composeapp.generated.resources.img_knight_dinar
import boqez.composeapp.generated.resources.img_knight_kupa
import boqez.composeapp.generated.resources.img_knight_spada
import boqez.composeapp.generated.resources.img_seven_baston
import boqez.composeapp.generated.resources.img_seven_dinar
import boqez.composeapp.generated.resources.img_seven_kupa
import boqez.composeapp.generated.resources.img_seven_spada
import boqez.composeapp.generated.resources.img_six_baston
import boqez.composeapp.generated.resources.img_six_dinar
import boqez.composeapp.generated.resources.img_six_kupa
import boqez.composeapp.generated.resources.img_six_spada
import boqez.composeapp.generated.resources.img_three_baston
import boqez.composeapp.generated.resources.img_three_dinar
import boqez.composeapp.generated.resources.img_three_kupa
import boqez.composeapp.generated.resources.img_three_spada
import boqez.composeapp.generated.resources.img_two_baston
import boqez.composeapp.generated.resources.img_two_dinar
import boqez.composeapp.generated.resources.img_two_kupa
import boqez.composeapp.generated.resources.img_two_spada
import com.lmuro.boqez.domain.model.Card
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.getString

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Composable
inline fun <reified T> Flow<T>.ObserveWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect(action)
        }
    }
}

fun String.isValidEmail(): Boolean =
    this.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))

suspend fun String.validatePassword(): String {
    return when {
        this.length < 8 -> getString(Res.string.error_password_min_lenght)
        this.length > 100 -> getString(Res.string.error_password_max_length)
        !this.matches(Regex(".*[0-9].*")) -> getString(Res.string.error_password_no_numbers)
        !this.matches(Regex(".*[a-zA-Z].*")) -> getString(Res.string.error_password_no_letter)
        !this.matches(Regex(".*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) -> getString(Res.string.error_password_no_special)
        else -> ""
    }
}

suspend fun String.validateEmail(): String {
    return when {
        this.isBlank() -> getString(Res.string.error_field_required)
        !this.isValidEmail() -> getString(Res.string.error_invalid_email)
        else -> ""
    }
}

suspend fun String.validatePasswordMin(): String {
    return when {
        this.isBlank() -> getString(Res.string.error_field_required)
        this.length < 8 -> getString(Res.string.error_password_min_lenght)
        this.length > 100 -> getString(Res.string.error_password_max_length)
        else -> ""
    }
}

suspend fun String.validateField(): String {
    return when {
        this.isBlank() -> getString(Res.string.error_field_required)
        this.length < 4 -> getString(Res.string.error_min_4_char)
        else -> ""
    }
}


suspend fun String.validateConfirmPassword(original: String): String {
    return when {
        this.isBlank() -> getString(Res.string.error_field_required)
        this != original -> getString(Res.string.error_passwords_no_match)
        else -> ""
    }
}

suspend fun String.validateUsername(): String {
    return when {
        this.isBlank() -> getString(Res.string.error_field_required)
        this.length < 3 -> getString(Res.string.error_min_3_char)
        this.length > 50 -> getString(Res.string.error_max_50_char)
        !this.matches(Regex("^[a-zA-Z0-9_]+$")) -> getString(Res.string.error_username_invalid_chars)
        else -> ""
    }
}


fun String.countryCodeToFlag(): String {
    if (this.length != 2) return "🏳️"

    val code = this.uppercase()
    val firstLetter = 0x1F1E6 + (code[0].code - 'A'.code)
    val secondLetter = 0x1F1E6 + (code[1].code - 'A'.code)

    return codePointToString(firstLetter) + codePointToString(secondLetter)
}

private fun codePointToString(codePoint: Int): String {
    val offset = codePoint - 0x10000
    val high = (offset ushr 10) + 0xD800
    val low = (offset and 0x3FF) + 0xDC00
    return charArrayOf(high.toChar(), low.toChar()).concatToString()
}

fun String.capitalize() : String {
    return replaceFirstChar { it.uppercase() }
}

fun Card.toDrawableResource(): DrawableResource {
    val rank = when (rank) {
        Rank.ACE    -> "ace"
        Rank.TWO    -> "two"
        Rank.THREE  -> "three"
        Rank.FOUR   -> "four"
        Rank.FIVE   -> "five"
        Rank.SIX    -> "six"
        Rank.SEVEN  -> "seven"
        Rank.JACK   -> "jack"
        Rank.KNIGHT -> "knight"
        Rank.KING   -> "king"
    }

    val suit = when (suit) {
        Suit.KUPA   -> "kupa"
        Suit.DINAR  -> "dinar"
        Suit.BASTON -> "baston"
        Suit.SPADA  -> "spada"
    }

    return when ("${rank}_${suit}") {
        "ace_baston"    -> Res.drawable.img_ace_baston
        "ace_kupa"      -> Res.drawable.img_ace_kupa
        "ace_dinar"     -> Res.drawable.img_ace_dinar
        "ace_spada"     -> Res.drawable.img_ace_spada
        "two_baston"    -> Res.drawable.img_two_baston
        "two_kupa"      -> Res.drawable.img_two_kupa
        "two_dinar"     -> Res.drawable.img_two_dinar
        "two_spada"     -> Res.drawable.img_two_spada
        "three_baston"  -> Res.drawable.img_three_baston
        "three_kupa"    -> Res.drawable.img_three_kupa
        "three_dinar"   -> Res.drawable.img_three_dinar
        "three_spada"   -> Res.drawable.img_three_spada
        "four_baston"   -> Res.drawable.img_four_baston
        "four_kupa"     -> Res.drawable.img_four_kupa
        "four_dinar"    -> Res.drawable.img_four_dinar
        "four_spada"    -> Res.drawable.img_four_spada
        "five_baston"   -> Res.drawable.img_five_baston
        "five_kupa"     -> Res.drawable.img_five_kupa
        "five_dinar"    -> Res.drawable.img_five_dinar
        "five_spada"    -> Res.drawable.img_five_spada
        "six_baston"    -> Res.drawable.img_six_baston
        "six_kupa"      -> Res.drawable.img_six_kupa
        "six_dinar"     -> Res.drawable.img_six_dinar
        "six_spada"     -> Res.drawable.img_six_spada
        "seven_baston"  -> Res.drawable.img_seven_baston
        "seven_kupa"    -> Res.drawable.img_seven_kupa
        "seven_dinar"   -> Res.drawable.img_seven_dinar
        "seven_spada"   -> Res.drawable.img_seven_spada
        "jack_baston"   -> Res.drawable.img_jack_baston
        "jack_kupa"     -> Res.drawable.img_jack_kupa
        "jack_dinar"    -> Res.drawable.img_jack_dinar
        "jack_spada"    -> Res.drawable.img_jack_spada
        "knight_baston" -> Res.drawable.img_knight_baston
        "knight_kupa"   -> Res.drawable.img_knight_kupa
        "knight_dinar"  -> Res.drawable.img_knight_dinar
        "knight_spada"  -> Res.drawable.img_knight_spada
        "king_baston"   -> Res.drawable.img_king_baston
        "king_kupa"     -> Res.drawable.img_king_kupa
        "king_dinar"    -> Res.drawable.img_king_dinar
        else            -> Res.drawable.img_king_spada
    }
}