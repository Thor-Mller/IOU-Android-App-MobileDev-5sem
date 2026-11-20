package com.freaky.iou.ui.groupsummary

data class MemberSpending(
    val userId: String,
    val username: String,
    val totalSpent: Int,
)

data class GroupSummaryState(
    val totalSpent: Int = 0,
    val members: List<MemberSpending> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
