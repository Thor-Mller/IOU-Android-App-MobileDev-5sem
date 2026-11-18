package com.freaky.iou.ui.data

import com.freaky.iou.ui.data.models.Group
import com.freaky.iou.ui.data.models.User
import com.google.firebase.dataconnect.generated.ExampleConnector
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.instance
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.dataconnect.generated.GetCurrentGroupsQuery
import com.google.firebase.dataconnect.generated.GetUsersInGroupQuery
import com.google.firebase.dataconnect.generated.SearchUsersByUsernameQuery
import com.google.firebase.dataconnect.generated.SearchUsersInGroupQuery
import kotlinx.coroutines.tasks.await
import java.util.UUID

object Database {

    private val auth by lazy { Firebase.auth }
    private val connector by lazy { ExampleConnector.instance }

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    /**
    -------------------- Database User Actions --------------------
     */
    suspend fun createAuthAccount(
        email: String,
        password: String,
    ): Result<FirebaseUser> = safeCall {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user ?: throw IllegalStateException("User creation returned null.")

        user
    }
    suspend fun createUserRecord(
        username: String,
        phone: String,
        email: String
    ): Result<Unit> = safeCall {
        val user = auth.currentUser ?: throw IllegalStateException("No signed-in user.")
        user.getIdToken(false).await()

        val existing = connector.getCurrentUser.execute().data.user
        if (existing == null) {
            connector.createCurrentUser.execute(
                username = username,
                phone = phone,
                email = email
            )
        }
    }
    suspend fun signUp(user: User): Result<Unit> = safeCall {
        createAuthAccount(user.email, user.password).getOrThrow()
        createUserRecord(user.username, user.phone ?: "", user.email).getOrThrow()
    }

    /**
     * Sign in using Firebase Auth
     */
    suspend fun signIn(user: User): Result<FirebaseUser> = safeCall {
        val result = auth.signInWithEmailAndPassword(user.email, user.password).await()
        result.user ?: throw IllegalStateException("Sign-in returned null.")
    }

    /**
     * Sends email verification to user
     */
    suspend fun sendEmailVerification(): Result<Unit> = safeCall {
        val user = auth.currentUser ?: throw IllegalStateException("No signed-in user.")
        user.sendEmailVerification().await()
    }

    suspend fun reloadUser(): Result<FirebaseUser?> = safeCall {
        auth.currentUser?.reload()?.await()
        auth.currentUser
    }

    fun signOut() {
        auth.signOut()
    }
    private fun ensureAuthenticated() {
        if (auth.currentUser == null) throw IllegalStateException("User is not signed in.")
    }

    suspend fun deleteCurrentUser(): Result<Unit> = safeCall {
        val user = auth.currentUser ?: throw IllegalStateException("User is not signed in.")
        connector.deleteCurrentUser.execute()
        user.delete().await()
    }

    /**
    -------------------- Database Expense Actions --------------------
     */
    suspend fun createExpense(
        groupId: UUID,
        name: String,
        amount: Double
    ): Result<UUID> = safeCall {
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
    ): Result<Unit> = safeCall {
        ensureAuthenticated()
        connector.addDebtorToExpense.execute(expenseId, debtorUserId, shareAmount)
    }

    suspend fun removeDebtorFromExpense(
        expenseId: UUID,
        debtorUserId: String,
    ): Result<Unit> = safeCall {
        ensureAuthenticated()
        connector.removeDebtorFromExpense.execute(expenseId, debtorUserId)
    }

    suspend fun deleteExpense(expenseId: UUID): Result<Unit> = safeCall {
        ensureAuthenticated()
        connector.deleteExpense.execute(expenseId)
    }
    suspend fun createGroup(name: String): Result<UUID> = safeCall {
        ensureAuthenticated()
        val response = connector.createGroup.execute(name = name)
        response.data.group.id
    }

    suspend fun updateGroup(groupId: UUID, name: String): Result<Unit> = safeCall {
        ensureAuthenticated()
        connector.updateGroup.execute(
            groupId = groupId,
            name = name
        )
    }

    suspend fun deleteGroup(groupId: UUID): Result<Unit> = safeCall {
        ensureAuthenticated()
        connector.deleteGroup.execute(groupId = groupId)
    }

// --- Group Members ---

    suspend fun getUsersInGroup(
        groupId: UUID
    ): Result<List<GetUsersInGroupQuery.Data.GroupUsersItem.User>> = safeCall {
        ensureAuthenticated()
        val response = connector.getUsersInGroup.execute(groupId = groupId)
        response.data.groupUsers.map { it.user }
    }

    suspend fun searchUsersInGroup(
        groupId: UUID,
        search: String
    ): Result<List<SearchUsersInGroupQuery.Data.GroupUsersItem.User>> = safeCall {
        ensureAuthenticated()
        val trimmed = search.trim()
        if (trimmed.isEmpty()) return@safeCall emptyList()

        val response = connector.searchUsersInGroup.execute(
            groupId = groupId,
            search = trimmed
        )
        response.data.groupUsers.map { it.user }
    }

    suspend fun addUserToGroup(groupId: UUID, userId: String): Result<Unit> = safeCall {
        ensureAuthenticated()
        connector.addUserToGroup.execute(
            groupId = groupId,
            userId = userId
        )
    }

    suspend fun removeUserFromGroup(groupId: UUID, userId: String): Result<Unit> = safeCall {
        ensureAuthenticated()
        connector.removeUserFromGroup.execute(
            groupId = groupId,
            userId = userId
        )
    }

    suspend fun searchUsersByUsername(search: String): Result<List<User>> = safeCall {
        ensureAuthenticated()
        val response = connector.searchUsersByUsername.execute(search = search)
        response.data.users.map {it.toUser()}
    }

    suspend fun getCurrentGroups(): Result<List<Group>> = safeCall {
        ensureAuthenticated()
        val response = connector.getCurrentGroups.execute()
        response.data.groupUsers.map {it.group.toGroup()}
    }
    private suspend inline fun <T> safeCall(block: () -> T): Result<T> =
        try {
            Result.success(block())
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Throwable) {
            Result.failure(e)
        }

    fun SearchUsersByUsernameQuery.Data.UsersItem.toUser(): User {
        return User(
            id = this.id,
            username = this.username,
            phone = this.phone,
            email = this.email,
        )
    }

    fun GetCurrentGroupsQuery.Data.GroupUsersItem.Group.Creator.toUser(): User {
        return User(
            id = this.id,
            username = this.username
        )
    }

    fun GetCurrentGroupsQuery.Data.GroupUsersItem.Group.toGroup(): Group {
        return Group(
            id = this.id,
            name = this.name,
            creator = this.creator.toUser()
        )
    }
}