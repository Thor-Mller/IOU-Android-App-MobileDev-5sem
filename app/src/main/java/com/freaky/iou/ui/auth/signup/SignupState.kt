package com.freaky.iou.ui.auth.signup

import androidx.compose.foundation.text.input.TextFieldState

data class SignupState(
    val usernameState: TextFieldState = TextFieldState(),
    val emailState: TextFieldState = TextFieldState(),
    val passwordState: TextFieldState = TextFieldState(),
    val confirmPasswordState: TextFieldState = TextFieldState(),
)