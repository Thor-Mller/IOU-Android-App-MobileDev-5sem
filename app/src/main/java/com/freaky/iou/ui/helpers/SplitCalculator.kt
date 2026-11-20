package com.freaky.iou.ui.helpers

import com.freaky.iou.ui.expense.NewExpenseViewModel

/**
 * Splits [totalBps] basis points equally between [members].
 * The leftover basis points go to the first members, so the result always sums to [totalBps].
 */
fun splitEquallyBps(
    members: Collection<String>,
    totalBps: Int = NewExpenseViewModel.FULL_SPLIT_BPS
): Map<String, Int> {
    if (members.isEmpty()) return emptyMap()
    val base = totalBps / members.size
    val remainder = totalBps % members.size
    return members.mapIndexed { index, id ->
        id to (base + if (index < remainder) 1 else 0)
    }.toMap()
}

/**
 * Converts each member's basis points into an exact amount in øre.
 * Rounding leftovers go to the members with the largest remainders, so the shares always sum to [totalCents].
 */
fun calculateExactShares(
    totalCents: Int,
    memberBps: Map<String, Int>
): Map<String, Int> {
    val entries = memberBps.entries.filter { it.value > 0 }
    val totalBps = entries.sumOf { it.value.toLong() }
    if (entries.isEmpty() || totalCents <= 0 || totalBps <= 0L) return emptyMap()

    val rawShares = entries.map { (id, bps) ->
        val numerator = totalCents.toLong() * bps.toLong()
        val base = (numerator / totalBps).toInt()
        val remainder = numerator % totalBps
        Triple(id, base, remainder)
    }

    val leftoverCents = totalCents - rawShares.sumOf { it.second }
    return rawShares
        .sortedByDescending { it.third }
        .mapIndexed { index, (id, base, _) ->
            id to (base + if (index < leftoverCents) 1 else 0)
        }
        .toMap()
}
