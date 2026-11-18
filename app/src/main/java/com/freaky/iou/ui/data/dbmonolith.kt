package com.freaky.iou.ui.data

import com.freaky.iou.ui.data.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.dataconnect.generated.ExampleConnector
import com.google.firebase.dataconnect.generated.GetUsersInGroupQuery
import com.google.firebase.dataconnect.generated.SearchUsersInGroupQuery
import com.google.firebase.dataconnect.generated.SearchUsersByUsernameQuery
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.instance
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
    suspend fun createAuthAccount(
        email: String,
        password: String,
    ): Result<FirebaseUser> = runCatching {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user ?: throw IllegalStateException("User creation returned null.")

        user
    }

    suspend fun signUp(user: User) {
        createAuthAccount(user.email, user.password)

        signIn(user)

        createUserRecord(user.username, user.phone ?: "", user.email)

        signOut()
    }

    /**
     * Replaces EmailPasswordActivity.signIn()
     */
    suspend fun signIn(user: User): Result<FirebaseUser> = runCatching {
        val result = auth.signInWithEmailAndPassword(user.email, user.password).await()
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

    suspend fun createExpense(
        groupId: UUID,
        name: String,
        amount: Double
    ): Result<UUID> = runCatching {
        ensureAuthenticated()
        connector.createExpense.execute(
            groupId = groupId,
            name = name,
            amount = amount
        ).data.expense_insert.id
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

    suspend fun createGroup(name: String): Result<UUID> = runCatching {
        val response = connector.createGroup.execute(name = name)
        response.data.group_insert.id
    }

    suspend fun updateGroup(groupId: UUID, name: String): Result<Unit> = runCatching {
        connector.updateGroup.execute(
            groupId = groupId,
            name = name
        )
    }

    suspend fun deleteGroup(groupId: UUID): Result<Unit> = runCatching {
        connector.deleteGroup.execute(groupId = groupId)
    }

// --- Group Members ---

    suspend fun getUsersInGroup(
        groupId: UUID
    ): Result<List<GetUsersInGroupQuery.Data.GroupUsersItem.User>> = runCatching {
        val response = connector.getUsersInGroup.execute(groupId = groupId)
        response.data.groupUsers.map { it.user }
    }

    suspend fun searchUsersInGroup(
        groupId: UUID,
        search: String
    ): Result<List<SearchUsersInGroupQuery.Data.GroupUsersItem.User>> = runCatching {
        val trimmed = search.trim()
        if (trimmed.isEmpty()) return@runCatching emptyList()

        val response = connector.searchUsersInGroup.execute(
            groupId = groupId,
            search = trimmed
        )
        response.data.groupUsers.map { it.user }
    }

    suspend fun addUserToGroup(groupId: UUID, userId: String): Result<Unit> = runCatching {
        connector.addUserToGroup.execute(
            groupId = groupId,
            userId = userId
        )
    }

    suspend fun removeUserFromGroup(groupId: UUID, userId: String): Result<Unit> = runCatching {
        connector.removeUserFromGroup.execute(
            groupId = groupId,
            userId = userId
        )
    }

    // Inside your Database object/class:
    suspend fun createUserRecord(username: String, phone: String, email: String) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        user.getIdToken(false).await() // Guarantee auth token is ready for @auth(level: USER)

        // Check if the row already exists so we don't fail or overwrite existing balances
        val existing = connector.getCurrentUser.execute().data.user
        if (existing == null) {
            connector.createCurrentUser.execute(
                username = username,
                phone = phone,
                email = email
            )
        }
    }

    suspend fun searchUsersByUsername(
        search: String
    ): Result<List<SearchUsersByUsernameQuery.Data.UsersItem>> = runCatching {
        ensureAuthenticated()
        val trimmed = search.trim()
        if (trimmed.isEmpty()) return@runCatching emptyList()

        val response = connector.searchUsersByUsername.execute(search = trimmed)
        response.data.users
    }
}