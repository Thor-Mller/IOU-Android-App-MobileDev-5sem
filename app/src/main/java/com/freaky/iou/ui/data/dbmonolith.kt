package com.freaky.iou.ui.data

import com.freaky.iou.ui.data.models.Expense
import com.freaky.iou.ui.data.models.Group
import com.freaky.iou.ui.data.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.dataconnect.generated.ExampleConnector
import com.google.firebase.dataconnect.generated.GetCurrentGroupsQuery
import com.google.firebase.dataconnect.generated.GetCurrentUserQuery
import com.google.firebase.dataconnect.generated.GetExpensesInGroupQuery
import com.google.firebase.dataconnect.generated.GetGroupByIdQuery
import com.google.firebase.dataconnect.generated.GetUserExpenseShareInGroupQuery
import com.google.firebase.dataconnect.generated.GetUsersInGroupQuery
import com.google.firebase.dataconnect.generated.SearchUsersByUsernameQuery
import com.google.firebase.dataconnect.generated.SearchUsersInGroupQuery
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
    -------------------- Database User Actions --------------------
     */
    suspend fun createAuthAccount(
        email: String,
        password: String,
    ): Result<FirebaseUser> = safeCall {
        val cleanEmail = email.trim().lowercase()
        val result = auth.createUserWithEmailAndPassword(cleanEmail, password).await()
        result.user ?: throw IllegalStateException("User creation returned null.")
    }

    suspend fun getCurrentUser(): Result<User> = safeCall {
        connector.getCurrentUser.execute().data.user?.toUser()
            ?: throw IllegalStateException("No user found")
    }
    suspend fun createUserRecord(
        username: String,
        phone: String?,
        email: String
    ): Result<Unit> = safeCall {
        val user = auth.currentUser ?: throw IllegalStateException("No signed-in user.")
        user.getIdToken(false).await()

        val cleanUsername = username.trim().lowercase()
        val cleanPhone = phone?.trim()?.ifBlank { null }
        val cleanEmail = email.trim().lowercase()

        val existing = connector.getCurrentUser.execute().data.user
        if (existing == null) {
            connector.createCurrentUser.execute(
                username = cleanUsername,
                email = cleanEmail,
            ) {
                this.phone = cleanPhone
            }
        }
    }
    suspend fun signUp(user: User): Result<Unit> = safeCall {
        val firebaseUser = createAuthAccount(user.email, user.password).getOrThrow()

        createUserRecord(user.username, user.phone, user.email)
            .onFailure { error ->
                firebaseUser.delete().await()
                throw error
            }
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
        amount: Int
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
        shareAmount: Int
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
    ): Result<List<User>> = safeCall {
        ensureAuthenticated()
        // Usernames and emails are stored lowercase, and `contains` is case-sensitive
        val trimmed = search.trim().lowercase()
        if (trimmed.isEmpty()) return@safeCall emptyList()

        val response = connector.searchUsersInGroup.execute(
            groupId = groupId,
            search = trimmed
        )
        response.data.groupUsers.map { it.user.toUser() }
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
        // Usernames are stored lowercase, and `contains` is case-sensitive
        val response = connector.searchUsersByUsername.execute(search = search.trim().lowercase())
        response.data.users.map {it.toUser()}
    }

    suspend fun getCurrentGroups(): Result<List<Group>> = safeCall {
        ensureAuthenticated()
        val response = connector.getCurrentGroups.execute()
        response.data.groupUsers.map {it.group.toGroup()}
    }

    suspend fun getExpensesInGroup(groupId: UUID): Result<List<Expense>> = safeCall {
        ensureAuthenticated()
        val response = connector.getExpensesInGroup.execute(groupId)
        response.data.expenses.map {it.toExpense()}
    }

    suspend fun getGroupData(groupId: UUID): Result<Group> = safeCall {
        ensureAuthenticated()
        val response = connector.getGroupById.execute(groupId)
        val groupDto = response.data.group
            ?: throw NoSuchElementException("Group not found for id: $groupId")
        groupDto.toGroup()
    }

    suspend fun getUserShareAmountInGroup(groupId: UUID): Result<Map<Expense, Int>> = safeCall {
        ensureAuthenticated()
        val response = connector.getUserExpenseShareInGroup.execute(groupId)

        response.data.expenseProviders.associate { item ->
            item.expense.toExpense() to (item.shareAmount ?: 0)
        }
    }

    suspend fun addPaymentToExpense(
        expenseId: UUID,
        paymentAmount: Int
    ): Result<Unit> = safeCall {
        ensureAuthenticated()
        require(paymentAmount > 0) { "Payment amount must be greater than 0." }

        connector.addPaymentToExpense.execute(
            expenseId = expenseId,
            paymentAmount = paymentAmount
        )
    }

    fun GetUserExpenseShareInGroupQuery.Data.ExpenseProvidersItem.Expense.toExpense(): Expense {
        return Expense(
            id = this.id,
            name = this.name,
            amount = this.amount
        )
    }
    private suspend inline fun <T> safeCall(block: () -> T): Result<T> =
        try {
            Result.success(block())
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
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

    fun GetGroupByIdQuery.Data.Group.toGroup(): Group {
        return Group(
            id = this.id,
            name = this.name,
            creator = this.creator.toUser()
        )
    }

    fun GetGroupByIdQuery.Data.Group.Creator.toUser(): User {
        return User(
            id = this.id,
            username = this.username
        )
    }
    fun GetCurrentGroupsQuery.Data.GroupUsersItem.Group.Creator.toUser(): User {
        return User(
            id = this.id,
            username = this.username
        )
    }

    fun GetExpensesInGroupQuery.Data.ExpensesItem.Group.Creator.toUser(): User {
        return User(
            id = this.id,
            username = this.username
        )
    }

    fun SearchUsersInGroupQuery.Data.GroupUsersItem.User.toUser(): User {
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

    fun GetExpensesInGroupQuery.Data.ExpensesItem.toExpense(): Expense {
        return Expense(
            id = this.id,
            user = User(id = this.userId),
            name = this.name,
            amount = this.amount,
            payed = this.payed,
            group = Group(
                id = this.group.id,
                creator = this.group.creator.toUser(),
                name = this.group.name
            )
        )
    }

    fun GetCurrentUserQuery.Data.User.toUser(): User {
        return User(
            id = this.id,
            email = this.email,
            phone = this.phone,
            username = this.username
        )
    }
}