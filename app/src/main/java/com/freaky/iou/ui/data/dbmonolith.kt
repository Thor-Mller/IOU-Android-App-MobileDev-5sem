package com.freaky.iou.data

import com.google.firebase.dataconnect.generated.ExampleConnector
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.instance
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import java.util.UUID

object Database {

    private val auth by lazy { Firebase.auth }
    private val connector by lazy { ExampleConnector.instance }

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    /**
     * Replaces EmailPasswordActivity.createAccount()
     * Creates the Firebase Auth user, sends verification if desired, and inserts the SQL User row.
     */
    suspend fun createAccount(
        email: String,
        password: String,
        username: String,
        phone: String
    ): Result<FirebaseUser> = runCatching {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user ?: throw IllegalStateException("User creation returned null.")

        // Create the matching row in PostgreSQL via Data Connect
        connector.createCurrentUser.execute(
            username = username,
            phone = phone,
            email = email
        )

        user
    }

    /**
     * Replaces EmailPasswordActivity.signIn()
     */
    suspend fun signIn(email: String, password: String): Result<FirebaseUser> = runCatching {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        result.user ?: throw IllegalStateException("Sign-in returned null.")
    }

    /**
     * Replaces EmailPasswordActivity.sendEmailVerification()
     */
    suspend fun sendEmailVerification(): Result<Unit> = runCatching {
        val user = auth.currentUser ?: throw IllegalStateException("No signed-in user.")
        user.sendEmailVerification().await()
    }

    /**
     * Replaces EmailPasswordActivity.reload() (refreshes token & emailVerified status)
     */
    suspend fun reloadUser(): Result<FirebaseUser?> = runCatching {
        auth.currentUser?.reload()?.await()
        auth.currentUser
    }

    fun signOut() {
        auth.signOut()
    }

    // --- Data Connect Mutations ---

    private fun ensureAuthenticated() {
        if (auth.currentUser == null) throw IllegalStateException("User is not signed in.")
    }

    suspend fun createExpense(name: String, amount: Double): Result<UUID> = runCatching {
        ensureAuthenticated()
        connector.createExpense.execute(name = name, amount = amount).data.expense_insert.id
    }

    suspend fun addDebtorToExpense(
        expenseId: UUID,
        debtorUserId: String,
        shareAmount: Double
    ): Result<Unit> = runCatching {
        ensureAuthenticated()
        connector.addDebtorToExpense.execute(expenseId, debtorUserId, shareAmount)
    }

    suspend fun settleUserShare(expenseId: UUID, debtorUserId: String): Result<Unit> = runCatching {
        ensureAuthenticated()
        connector.settleUserShare.execute(expenseId, debtorUserId)
    }

    suspend fun deleteExpense(expenseId: UUID): Result<Unit> = runCatching {
        ensureAuthenticated()
        connector.deleteExpense.execute(expenseId)
    }
}