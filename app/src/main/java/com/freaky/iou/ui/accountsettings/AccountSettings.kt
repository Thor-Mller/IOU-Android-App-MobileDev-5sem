package com.freaky.iou.ui.accountsettings

import androidx.compose.foundation.text.input.TextFieldState

data class AccountSettings(
    var username: TextFieldState = TextFieldState(),
    var phone: TextFieldState = TextFieldState(),
    var email: TextFieldState = TextFieldState(),
)