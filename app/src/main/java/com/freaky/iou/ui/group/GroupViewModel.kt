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

            groupResult
                .onSuccess { group ->
                    groupState.groupNameState.setTextAndPlaceCursorAtEnd(group.name)
                    groupState = groupState.copy(group = group)
                }
                .onFailure { throwable ->
                    groupState = groupState.copy(error = throwable.message)
                }

            expensesResult
                .onSuccess { expenses ->
                    groupState = groupState.copy(expenses = expenses)
                }
                .onFailure { throwable ->
                    groupState = groupState.copy(error = throwable.message)
                }

            userSharesResult
                .onSuccess { userShares ->
                    groupState = groupState.copy(userShares = userShares)
                }
                .onFailure { throwable ->
                    groupState = groupState.copy(error = throwable.message)
                }

            groupState = groupState.copy(isLoading = false)
        }
    }
}