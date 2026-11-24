package com.freaky.iou.ui.payment.states

import com.freaky.iou.ui.helpers.splitEquallyBps
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

class ExpenseStateTest {

    private fun stateWith(vararg bps: Int) = ExpenseState(
        title = "Dinner",
        totalAmount = 10_000,
        groupId = UUID.randomUUID(),
        memberPercentages = bps.mapIndexed { i, v -> "member$i" to v }.toMap()
    )

    @Test
    fun isSplitValid_exactly100Percent_isValid() {
        assertTrue(stateWith(10_000).isSplitValid)
        assertTrue(stateWith(5_000, 5_000).isSplitValid)
        assertTrue(stateWith(3_334, 3_333, 3_333).isSplitValid)
        assertTrue(stateWith(1, 9_999).isSplitValid)
    }

    @Test
    fun isSplitValid_withZeroPercentMember_isValidWhenRestSumsTo100() {
        assertTrue(stateWith(5_000, 0, 5_000).isSplitValid)
    }

    @Test
    fun isSplitValid_oneBasisPointOff_isInvalid() {
        assertFalse(stateWith(9_999).isSplitValid)
        assertFalse(stateWith(10_001).isSplitValid)
        assertFalse(stateWith(3_333, 3_333, 3_333).isSplitValid)
        assertFalse(stateWith(5_000, 5_001).isSplitValid)
    }

    @Test
    fun isSplitValid_farFrom100Percent_isInvalid() {
        assertFalse(stateWith(0).isSplitValid)
        assertFalse(stateWith(0, 0).isSplitValid)
        assertFalse(stateWith(10_000, 10_000).isSplitValid)
    }

    @Test
    fun isSplitValid_noMembers_isInvalid() {
        assertFalse(stateWith().isSplitValid)
    }

    @Test
    fun isSplitValid_equalSplit_isAlwaysValid() {
        for (count in 1..200) {
            val state = ExpenseState(
                title = "Dinner",
                totalAmount = 10_000,
                groupId = UUID.randomUUID(),
                memberPercentages = splitEquallyBps((1..count).map { "member$it" })
            )
            assertTrue("$count members", state.isSplitValid)
        }
    }

    @Test
    fun isSplitValid_totalThatOverflowsIntTo100Percent_isInvalid() {
        // Int.MAX_VALUE * 2 + 10_002 wraps around to exactly 10_000 in Int arithmetic
        assertFalse(stateWith(Int.MAX_VALUE, Int.MAX_VALUE, 10_002).isSplitValid)
    }
}
