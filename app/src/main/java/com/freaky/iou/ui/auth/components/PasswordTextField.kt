package com.freaky.iou.ui.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PasswordTextField(state: TextFieldState, modifier: Modifier = Modifier) {
    OutlinedSecureTextField(
        state = state,
        modifier = modifier.fillMaxWidth(),
        label = { Text("Password") },
    )
}