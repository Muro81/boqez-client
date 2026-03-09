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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
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