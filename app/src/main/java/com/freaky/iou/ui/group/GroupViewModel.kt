package com.freaky.iou.ui.group

import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freaky.iou.ui.data.Database
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.UUID

class GroupViewModel(
    private val groupId: UUID
) : ViewModel() {

    var groupState by mutableStateOf(GroupState())
        private set

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            groupState = groupState.copy(isLoading = true, error = null)

            val groupDeferred = async { Database.getGroupData(groupId) }
            val expensesDeferred = async { Database.getExpensesInGroup(groupId) }
            val userSharesDeferred = async { Database.getUserShareAmountInGroup(groupId) }

            val groupResult = groupDeferred.await()
            val expensesResult = expensesDeferred.await()
            val userSharesResult = userSharesDeferred.await()

            groupResult.onSuccess { group ->
                groupState.groupNameState.setTextAndPlaceCursorAtEnd(group.name)
            }

            val (activeExpenses, archivedExpenses) = expensesResult.getOrNull()
                ?.partition { it.payed < it.amount }
                ?: (groupState.activeExpenses to groupState.archivedExpenses)

            val firstError = listOf(groupResult, expensesResult, userSharesResult)
                .firstNotNullOfOrNull { it.exceptionOrNull()?.message }

            groupState = groupState.copy(
                group = groupResult.getOrDefault(groupState.group),
                activeExpenses = activeExpenses,
                archivedExpenses = archivedExpenses,
                userShares = userSharesResult.getOrDefault(groupState.userShares),
                error = firstError,
                isLoading = false
            )
        }
    }
}