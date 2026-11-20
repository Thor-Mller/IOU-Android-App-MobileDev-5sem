package com.freaky.iou.ui.groupsummary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freaky.iou.ui.data.Database
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.UUID

class GroupSummaryViewModel(
    private val groupId: UUID
) : ViewModel() {

    var state by mutableStateOf(GroupSummaryState())
        private set

    // Loaded from the screen's ON_RESUME, which also fires on first display
    fun refresh() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            val expensesDeferred = async { Database.getExpensesInGroup(groupId) }
            val membersDeferred = async { Database.getUsersInGroup(groupId) }

            val expensesResult = expensesDeferred.await()
            val membersResult = membersDeferred.await()

            val firstError = listOf(expensesResult, membersResult)
                .firstNotNullOfOrNull { it.exceptionOrNull()?.message }

            if (firstError != null) {
                state = state.copy(error = firstError, isLoading = false)
                return@launch
            }

            val expenses = expensesResult.getOrThrow()

            // Expenses only carry the payer's id, so spending is summed per userId
            val spentByUser = expenses.groupBy { it.user.id }
                .mapValues { (_, paid) -> paid.sumOf { it.amount } }

            val members = membersResult.getOrThrow()
                .map { member ->
                    MemberSpending(
                        userId = member.id,
                        username = member.username,
                        totalSpent = spentByUser[member.id] ?: 0
                    )
                }
                .sortedByDescending { it.totalSpent }

            state = state.copy(
                totalSpent = expenses.sumOf { it.amount },
                members = members,
                isLoading = false
            )
        }
    }
}
