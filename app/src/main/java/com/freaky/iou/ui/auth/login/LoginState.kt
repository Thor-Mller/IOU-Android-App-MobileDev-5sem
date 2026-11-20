package com.freaky.iou.ui.auth.login

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState(
    val emailState: TextFieldState = TextFieldState(),
    val passwordState: TextFieldState = TextFieldState(),
    val error: String = "",
)