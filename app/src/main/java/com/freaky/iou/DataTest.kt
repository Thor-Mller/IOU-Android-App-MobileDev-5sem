package com.example.iou.data

import com.google.firebase.dataconnect.generated.ExampleConnector
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.instance
import java.util.UUID

class ExpenseRepository {
    private val connector = ExampleConnector.instance

    // 1. Create or update the logged-in user's profile
    suspend fun upsertUser(userId: String, username: String, phone: String) {
        connector.upsertCurrentUser.execute(
            userId = userId, username = username, phone = phone
        )
    }

    // 2. Create an expense and immediately attach a debtor to it
    suspend fun createExpenseWithSplit(
        userId: String, name: String, totalAmount: Double, debtorUserId: String, shareAmount: Double
    ): UUID {
        // Run CreateExpense and extract the generated UUID from the response
        val result = connector.createExpense.execute(
            userId = userId, name = name, amount = totalAmount
        )
        val expenseId: UUID = result.data.expense_insert.id

        // Pass that UUID into AddDebtorToExpense
        connector.addDebtorToExpense.execute(
            expenseId = expenseId, debtorUserId = debtorUserId, shareAmount = shareAmount
        )

        return expenseId
    }

    // 3. Mark a debtor's share as paid
    suspend fun settleShare(expenseId: UUID, debtorUserId: String) {
        connector.settleUserShare.execute(
            expenseId = expenseId, debtorUserId = debtorUserId
        )
    }

    // 4. Delete an expense
    suspend fun deleteExpense(expenseId: UUID) {
        connector.deleteExpense.execute(
            expenseId = expenseId
        )
    }
}