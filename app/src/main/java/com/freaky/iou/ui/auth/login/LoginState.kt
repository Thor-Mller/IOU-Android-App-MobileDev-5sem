package com.freaky.iou.ui.auth.login

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState(
    val usernameState: TextFieldState = TextFieldState(),
    val passwordState: TextFieldState = TextFieldState(),
)