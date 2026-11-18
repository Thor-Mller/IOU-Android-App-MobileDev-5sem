package com.freaky.iou.ui.group

import androidx.compose.foundation.text.input.TextFieldState
import com.freaky.iou.ui.data.models.User

data class NewGroupState(
    val groupNameState: TextFieldState = TextFieldState(),
    val usernameState: TextFieldState = TextFieldState(),
    val members: List<User> = emptyList(),
)