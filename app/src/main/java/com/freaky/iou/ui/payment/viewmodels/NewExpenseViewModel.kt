package com.freaky.iou.ui.expense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freaky.iou.ui.data.Database
import com.freaky.iou.domain.model.Expense
import com.google.firebase.dataconnect.generated.SearchUsersInGroupQuery
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.collections.plus
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class NewExpenseViewModel(
    initialTitle: String = "",
    initialAmount: Double = 0.0,
    initialGroupId: UUID = UUID.randomUUID(),
    initialMembers: List<String> = listOfNotNull(Database.currentUser?.uid)
) : ViewModel() {

    init {
        loadGroupMembers(initialGroupId)
    }
    var uiState by mutableStateOf(
        Expense(
            title = initialTitle,
            groupId = initialGroupId,
            totalAmount = initialAmount,
            memberPercentages = if (initialMembers.isNotEmpty()) {
                val equalShare = 100f / initialMembers.size
                initialMembers.associateWith { equalShare }
            } else {
                emptyMap()
            },
        ))
        private set

    fun setGroupId(groupId: UUID) {
        if (uiState.groupId != groupId) {
            uiState = uiState.copy(groupId = groupId)
        }
        loadGroupMembers(groupId)
    }

    fun loadGroupMembers(groupId: UUID) {
        viewModelScope.launch {
            Database.getUsersInGroup(groupId).onSuccess { users ->
                val currentMembers = uiState.getMembers().toSet()
                val resolvedMap = users
                    .filter { it.id in currentMembers }
                    .associate { it.username to it.id }

                uiState = uiState.copy(
                    memberNamesToIds = uiState.memberNamesToIds + resolvedMap
                )
            }
        }
    }
    var isSaving by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set
    var searchResults by mutableStateOf<List<SearchUsersInGroupQuery.Data.GroupUsersItem.User>>(
        emptyList()
    )
        private set
    var selectedSearchUser by mutableStateOf<SearchUsersInGroupQuery.Data.GroupUsersItem.User?>(null)
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
        val memberList = uiState.getMembers().toList()
        if (memberList.isEmpty()) return
        val equalShare = 100f / memberList.size
        val updated = memberList.associateWith { equalShare }
        uiState = uiState.copy(memberPercentages = updated)
    }

    fun addDebtor(userId: String, username: String? = null) {
        val cleanId = userId.trim()
        if (cleanId.isBlank() || uiState.getMembers().contains(cleanId)) return

        val updatedMembers = uiState.getMembers() + cleanId
        val updatedMap = if (!username.isNullOrBlank()) {
            uiState.memberNamesToIds + (username to cleanId)
        } else {
            uiState.memberNamesToIds
        }

        uiState = uiState.copy(
            memberPercentages = updatedMembers.associateWith { 0f },
            memberNamesToIds = updatedMap
        )

        splitEqually()
    }

    fun removeDebtor(debtorUserId: String) {
        if (!uiState.getMembers().contains(debtorUserId)) return

        uiState = uiState.copy(
            memberPercentages = uiState.memberPercentages - debtorUserId,
            memberNamesToIds = uiState.memberNamesToIds.filterValues { it != debtorUserId }
        )
    }

    fun submitExpense(onSuccess: (Expense) -> Unit) {
        if (!uiState.isSplitValid || uiState.title.isBlank() || uiState.totalAmount <= 0.0) return
        if (isSaving) return

        viewModelScope.launch {
            isSaving = true
            errorMessage = null

            // 1. Create the main Expense row
            val expenseResult = Database.createExpense(
                name = uiState.title.trim(), amount = uiState.totalAmount, groupId = uiState.groupId
            )

            expenseResult.fold(onSuccess = { expenseId ->
                val participants = uiState.getMembers().filter { memberId ->
                    uiState.amountForMember(memberId) > 0.0
                }

                for (memberId in participants) {
                    val share = uiState.amountForMember(memberId)
                    val result = Database.addDebtorToExpense(
                        expenseId = expenseId,
                        debtorUserId = memberId,
                        shareAmount = share
                    )

                    if (result.isFailure) {
                        Database.deleteExpense(expenseId)
                        isSaving = false
                        errorMessage = result.exceptionOrNull()?.localizedMessage
                            ?: "Failed to add member to expense."
                        return@launch
                    }
                }

                isSaving = false
                onSuccess(uiState)
            }, onFailure = { error ->
                isSaving = false
                errorMessage = error.localizedMessage ?: "Failed to create expense."
            })
        }
    }

    suspend fun searchUsersInGroup(groupId: UUID, query: String) {
        if (query.isBlank()) {
            searchResults = emptyList()
            return
        }
        Database.searchUsersInGroup(groupId, query)
            .onSuccess { users ->
                val existingIds = uiState.getMembers().toSet()
                searchResults = users.filterNot { it.id in existingIds }
            }
            .onFailure {
                searchResults = emptyList()
            }
    }

    fun selectSearchUser(user: SearchUsersInGroupQuery.Data.GroupUsersItem.User) {
        selectedSearchUser = user
        searchResults = emptyList()
    }
    fun clearUserSearch() {
        selectedSearchUser = null
        searchResults = emptyList()
    }
}