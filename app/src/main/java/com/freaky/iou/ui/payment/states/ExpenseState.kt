package com.freaky.iou.ui.payment.states

import com.freaky.iou.ui.expense.NewExpenseViewModel
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.Instant

data class ExpenseState(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val totalAmount: Int,
    val date: Instant = Clock.System.now(),
    val memberPercentages: Map<String, Int> = emptyMap(),
    val groupId: UUID,
    val memberNamesToIds: Map<String, String> = emptyMap()
) {
    fun getMembers(): Set<String> {
        return memberPercentages.keys
    }
    fun amountForMember(memberId: String): Int {
        val bps = memberPercentages[memberId] ?: return 0
        return ((totalAmount.toLong() * bps) / NewExpenseViewModel.FULL_SPLIT_BPS).toInt()
    }
    val isSplitValid: Boolean
        get() = memberPercentages.isNotEmpty() &&
                memberPercentages.values.sum() == NewExpenseViewModel.FULL_SPLIT_BPS
}