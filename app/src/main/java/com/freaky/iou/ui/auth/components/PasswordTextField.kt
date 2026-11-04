package com.freaky.iou.ui.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class PasswordError {
    TooShort, NonMatching
}

@Composable
fun PasswordTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    required: Boolean = false,
    prefix: String = "",
    error: PasswordError? = null
) {
    val supportingText = when (error) {
        PasswordError.TooShort -> "too short!"
        PasswordError.NonMatching -> "not matching!"
        null -> if (required) "*required" else null
    }

    OutlinedSecureTextField(
        state = state,
        modifier = modifier.fillMaxWidth(),
        label = {
            Text(
                if (required) "${prefix}Password*"
                else "${prefix}Password"
            )
        },
        supportingText = if (supportingText != null) {
            { Text(supportingText) }
        } else null,
    )
}