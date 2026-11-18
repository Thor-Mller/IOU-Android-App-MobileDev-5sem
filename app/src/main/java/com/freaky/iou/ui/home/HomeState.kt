package com.freaky.iou.ui.home

import androidx.compose.foundation.text.input.TextFieldState
import com.freaky.iou.ui.data.models.Group

data class HomeState (
    val searchState: TextFieldState = TextFieldState(""),
    val groups: List<Group> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)