package com.freaky.iou.ui.group

import androidx.compose.foundation.text.input.TextFieldState
import com.freaky.iou.ui.data.models.Expense
import com.freaky.iou.ui.data.models.Group

data class GroupState(
    val group: Group? = null,
    val groupNameState: TextFieldState = TextFieldState(""),
    val expenses: List<Expense> = emptyList(),
    val userShares: Map<Expense, Float> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)