package com.freaky.iou.ui.helpers

import com.freaky.iou.ui.expense.NewExpenseViewModel.Companion.FULL_SPLIT_BPS
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SplitCalculatorTest {

    private fun members(count: Int) = (1..count).map { "member$it" }

    // splitEquallyBps

    @Test
    fun splitEqually_alwaysSumsToExactly100Percent() {
        for (count in 1..200) {
            val split = splitEquallyBps(members(count))
            assertEquals("sum for $count members", FULL_SPLIT_BPS, split.values.sum())
        }
    }

    @Test
    fun splitEqually_sharesDifferByAtMostOneBasisPoint() {
        for (count in 1..200) {
            val values = splitEquallyBps(members(count)).values
            assertTrue("spread for $count members", values.max() - values.min() <= 1)
        }
    }

    @Test
    fun splitEqually_includesEveryMember() {
        for (count in 1..200) {
            val ids = members(count)
            assertEquals("members for $count", ids.toSet(), splitEquallyBps(ids).keys)
        }
    }

    @Test
    fun splitEqually_threeMembers_leftoverGoesToFirstMember() {
        val split = splitEquallyBps(listOf("a", "b", "c"))
        assertEquals(mapOf("a" to 3334, "b" to 3333, "c" to 3333), split)
    }

    @Test
    fun splitEqually_singleMember_gets100Percent() {
        assertEquals(mapOf("a" to FULL_SPLIT_BPS), splitEquallyBps(listOf("a")))
    }

    @Test
    fun splitEqually_noMembers_isEmpty() {
        assertTrue(splitEquallyBps(emptyList()).isEmpty())
    }

    // calculateExactShares

    @Test
    fun exactShares_equalSplit_neverLosesOrInventsOre() {
        val totals = (1..1_000) + listOf(9_999, 10_000, 10_001, 123_457, 99_999_999, Int.MAX_VALUE)
        for (count in 1..12) {
            val bps = splitEquallyBps(members(count))
            for (total in totals) {
                val shares = calculateExactShares(total, bps)
                assertEquals("$total øre, $count members", total.toLong(), shares.values.sumOf { it.toLong() })
            }
        }
    }

    @Test
    fun exactShares_unevenSplits_neverLoseOrInventOre() {
        val splits = listOf(
            listOf(1, 9_999),
            listOf(3_333, 3_333, 3_334),
            listOf(1, 1, 9_998),
            listOf(2_500, 2_500, 2_500, 2_500),
            listOf(7_001, 1_499, 1_500),
            listOf(10_000)
        )
        for (split in splits) {
            val bps = split.mapIndexed { i, v -> "member$i" to v }.toMap()
            for (total in (1..2_000) + Int.MAX_VALUE) {
                val shares = calculateExactShares(total, bps)
                assertEquals("$total øre, split $split", total.toLong(), shares.values.sumOf { it.toLong() })
            }
        }
    }

    @Test
    fun exactShares_eachShareIsWithinOneOreOfExactValue() {
        val bps = mapOf("a" to 7_001, "b" to 1_499, "c" to 1_500)
        for (total in 1..2_000) {
            val shares = calculateExactShares(total, bps)
            for ((id, share) in shares) {
                val exact = total.toLong() * bps.getValue(id)
                val floor = exact / FULL_SPLIT_BPS
                val ceil = (exact + FULL_SPLIT_BPS - 1) / FULL_SPLIT_BPS
                assertTrue("$id at $total øre got $share", share in floor..ceil)
            }
        }
    }

    @Test
    fun exactShares_100krBetweenThree() {
        val shares = calculateExactShares(10_000, splitEquallyBps(listOf("a", "b", "c")))
        assertEquals(mapOf("a" to 3_334, "b" to 3_333, "c" to 3_333), shares)
    }

    @Test
    fun exactShares_oneOreBetweenThree_onlyOneMemberPays() {
        val shares = calculateExactShares(1, splitEquallyBps(listOf("a", "b", "c")))
        assertEquals(1, shares.values.sum())
        assertEquals(1, shares.values.count { it == 1 })
    }

    @Test
    fun exactShares_zeroOrNegativeTotal_isEmpty() {
        val bps = splitEquallyBps(listOf("a", "b"))
        assertTrue(calculateExactShares(0, bps).isEmpty())
        assertTrue(calculateExactShares(-100, bps).isEmpty())
    }

    // Members at 0%

    @Test
    fun exactShares_memberAtZeroPercent_isLeftOut() {
        val shares = calculateExactShares(10_000, mapOf("a" to 5_000, "zero" to 0, "b" to 5_000))
        assertFalse(shares.containsKey("zero"))
        assertEquals(mapOf("a" to 5_000, "b" to 5_000), shares)
    }

    @Test
    fun exactShares_zeroPercentMember_doesNotChangeOtherShares() {
        val withoutZero = mapOf("a" to 3_333, "b" to 3_333, "c" to 3_334)
        val withZero = withoutZero + ("zero" to 0)
        for (total in 1..2_000) {
            assertEquals("$total øre", calculateExactShares(total, withoutZero), calculateExactShares(total, withZero))
        }
    }

    @Test
    fun exactShares_everyMemberAtZeroPercent_isEmpty() {
        assertTrue(calculateExactShares(10_000, mapOf("a" to 0, "b" to 0)).isEmpty())
    }
}
