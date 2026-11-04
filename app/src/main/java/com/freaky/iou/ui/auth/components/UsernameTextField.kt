package com.freaky.iou.ui.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UsernameTextField(
    state: TextFieldState, modifier: Modifier = Modifier, required: Boolean = false
) {
    OutlinedTextField(
        state = state,
        modifier = modifier.fillMaxWidth(),
        label = {
            Text(
                if (required) "Username*"
                else "Username"
            )
        },
        lineLimits = TextFieldLineLimits.SingleLine,
        supportingText = if (required) {
            { Text("*required") }
        } else null,
    )
}