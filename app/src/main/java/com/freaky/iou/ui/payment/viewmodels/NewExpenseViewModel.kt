package com.freaky.iou.ui.expense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.freaky.iou.domain.model.Expense
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class NewExpenseViewModel(
    initialTitle: String = "",
    initialAmount: Double = 0.0,
    initialMembers: List<String> = emptyList()
) : ViewModel() {

    var uiState by mutableStateOf(
        Expense(
            title = initialTitle,
            totalAmount = initialAmount,
            memberPercentages = if (initialMembers.isNotEmpty()) {
                val equalShare = 100f / initialMembers.size
                initialMembers.associateWith { equalShare }
            } else {
                emptyMap()
            }
        )
    )
        private set

    fun onTitleChange(newTitle: String) {
        uiState = uiState.copy(title = newTitle)
    }

    fun onTotalAmountChange(newAmount: Double) {
        uiState = uiState.copy(totalAmount = newAmount)
    }

    fun onTotalAmountTextChange(text: String) {
        val parsed = text.replace(',', '.').toDoubleOrNull() ?: 0.0
        uiState = uiState.copy(totalAmount = parsed)
    }

    fun onMemberPercentageChange(memberId: String, percentage: Float) {
        val updated = uiState.memberPercentages + (memberId to percentage)
        uiState = uiState.copy(memberPercentages = updated)
    }

    fun splitEqually() {
        val memberList = uiState.members.toList()
        if (memberList.isEmpty()) return
        val equalShare = 100f / memberList.size
        val updated = memberList.associateWith { equalShare }
        uiState = uiState.copy(memberPercentages = updated)
    }
}