package com.freaky.iou.ui.expense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freaky.iou.ui.data.Database
import com.freaky.iou.ui.data.models.User
import com.freaky.iou.ui.helpers.calculateExactShares
import com.freaky.iou.ui.helpers.splitEquallyBps
import com.freaky.iou.ui.payment.states.ExpenseState
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.UUID

class NewExpenseViewModel(
    initialTitle: String = "",
    initialAmount: Int = 0,
    initialGroupId: UUID = UUID.randomUUID(),
    initialMembers: List<String> = listOfNotNull(Database.currentUser?.uid)
) : ViewModel() {

    companion object {
        // 10,000 basis points = 100.00% (100 bps = 1%)
        const val FULL_SPLIT_BPS = 10_000
    }

    var uiState by mutableStateOf(
        ExpenseState(
            title = initialTitle,
            groupId = initialGroupId,
            totalAmount = initialAmount,
            memberPercentages = splitEquallyBps(initialMembers)
        )
    )
        private set

    var isSaving by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var searchResults by mutableStateOf<List<User>>(emptyList())
        private set

    var selectedSearchUser by mutableStateOf(User())
        private set

    init {
        loadGroupMembers(initialGroupId)
    }

    fun loadGroupMembers(groupId: UUID) {
        viewModelScope.launch {
            Database.getUsersInGroup(groupId).onSuccess { users ->
                // Start the split with every group member, divided equally
                val memberIds = uiState.getMembers() + users.map { it.id }

                uiState = uiState.copy(
                    memberPercentages = splitEquallyBps(memberIds),
                    memberNamesToIds = uiState.memberNamesToIds + users.associate { it.username to it.id }
                )
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        uiState = uiState.copy(title = newTitle)
    }

    fun onTotalAmountTextChange(text: String) {
        val normalized = text.trim().replace(',', '.')
        val cents = normalized.toBigDecimalOrNull()
            ?.movePointRight(2)
            ?.setScale(0, RoundingMode.HALF_UP)
            ?.toLong()
            ?.coerceIn(0L, Int.MAX_VALUE.toLong())
            ?.toInt()
            ?: 0
        uiState = uiState.copy(totalAmount = cents)
    }

    /**
     * Pass percentage in basis points (e.g., 5000 = 50.00%, 3333 = 33.33%).
     * Convert slider/text input to Int basis points on the screen before calling.
     */
    fun onMemberPercentageChange(memberId: String, basisPoints: Int) {
        val clamped = basisPoints.coerceIn(0, FULL_SPLIT_BPS)
        val updated = uiState.memberPercentages + (memberId to clamped)
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
            memberPercentages = splitEquallyBps(updatedMembers),
            memberNamesToIds = updatedMap
        )
    }

    fun removeDebtor(debtorUserId: String) {
        if (!uiState.getMembers().contains(debtorUserId)) return

        val remainingMembers = uiState.getMembers().filterNot { it == debtorUserId }
        uiState = uiState.copy(
            memberPercentages = splitEquallyBps(remainingMembers),
            memberNamesToIds = uiState.memberNamesToIds.filterValues { it != debtorUserId }
        )
    }

    fun submitExpense(onSuccess: (ExpenseState) -> Unit) {
        if (!uiState.isSplitValid || uiState.title.isBlank() || uiState.totalAmount <= 0) return
        if (isSaving) return

        viewModelScope.launch {
            isSaving = true
            errorMessage = null

            val exactShares = calculateExactShares(
                totalCents = uiState.totalAmount,
                memberBps = uiState.memberPercentages
            )

            val currentUser = Database.getCurrentUser().getOrDefault(User())
            val currentUserShare = exactShares[currentUser.id]

            val expenseResult = Database.createExpense(
                name = uiState.title.trim(),
                amount = uiState.totalAmount,
                groupId = uiState.groupId
            )

            expenseResult.fold(
                onSuccess = { expenseId ->
                    for ((memberId, shareCents) in exactShares) {
                        if (shareCents <= 0) continue

                        val result = Database.addDebtorToExpense(
                            expenseId = expenseId,
                            debtorUserId = memberId,
                            shareAmount = shareCents
                        )

                        if (result.isFailure) {
                            Database.deleteExpense(expenseId)
                            isSaving = false
                            errorMessage = result.exceptionOrNull()?.localizedMessage
                                ?: "Failed to add member to expense."
                            return@launch
                        }
                    }

                    if (currentUserShare != null) {
                        Database.addPaymentToExpense(expenseId, currentUserShare)
                    }

                    isSaving = false
                    onSuccess(uiState)
                },
                onFailure = { error ->
                    isSaving = false
                    errorMessage = error.localizedMessage ?: "Failed to create expense."
                }
            )
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

    fun selectSearchUser(user: User) {
        selectedSearchUser = user
        searchResults = emptyList()
    }

    fun clearUserSearch() {
        selectedSearchUser = User()
        searchResults = emptyList()
    }

}