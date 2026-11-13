package com.freaky.iou.domain.model

import java.util.UUID
import kotlin.math.roundToInt
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Expense @OptIn(ExperimentalTime::class) constructor(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val totalAmount: Double,
    val date: Instant = Clock.System.now(),
    val memberPercentages: Map<String, Float> = emptyMap()
) {
    val members: Set<String>
        get() = memberPercentages.keys

    fun amountForMember(memberId: String): Double {
        val percentage = memberPercentages[memberId] ?: 0f
        return (totalAmount * (percentage / 100.0) * 100).roundToInt() / 100.0
    }

    val isSplitValid: Boolean
        get() = memberPercentages.values.sum() in 99.9f..100.1f
}