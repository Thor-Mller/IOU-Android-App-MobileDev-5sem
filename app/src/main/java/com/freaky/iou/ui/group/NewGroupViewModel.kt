package com.freaky.iou.ui.group

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freaky.iou.ui.data.Database
import com.freaky.iou.ui.data.models.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class NewGroupViewModel : ViewModel() {

    val isFormValid: Boolean by derivedStateOf {
        state.groupNameState.text.isNotBlank() && state.members.isNotEmpty()
    }

    var state: NewGroupState by mutableStateOf(NewGroupState())
        private set

    var searchResults: List<User> by mutableStateOf(emptyList())
        private set

    var isSearching: Boolean by mutableStateOf(false)
        private set

    var isCreating: Boolean by mutableStateOf(false)
        private set

    var errorMessage: String? by mutableStateOf(null)
        private set

    private var searchJob: Job? = null

    /**
     * Call this whenever state.usernameState.text changes (e.g. via LaunchedEffect in your screen).
     */
    fun onUsernameQueryChanged(query: String) {
        searchJob?.cancel()
        val trimmed = query.trim()

        if (trimmed.length < 2) {
            searchResults = emptyList()
            isSearching = false
            return
        }

        searchJob = viewModelScope.launch {
            delay(300.milliseconds) // Debounce typing
            isSearching = true
            errorMessage = null

            Database.searchUsersByUsername(trimmed)
                .onSuccess { users ->
                    val existingIds = state.members.map { it.id }.toSet()
                    val currentUid = Database.currentUser?.uid

                    searchResults = users
                        .filter { it.id !in existingIds && it.id != currentUid }
                        .map { User(it.id, it.username, it.email, it.phone) }
                }
                .onFailure { error ->
                    errorMessage = error.message ?: "Failed to search users"
                    searchResults = emptyList()
                }

            isSearching = false
        }
    }

    /**
     * Adds a specific user picked from the search dropdown.
     */
    fun onAddMember(member: User) {
        if (state.members.any { it.id == member.id }) return

        state = state.copy(members = state.members + member)
        state.usernameState.clearText()
        searchResults = emptyList()
        errorMessage = null
    }

    fun removeMember(member: User) {
        state = state.copy(members = state.members - member)
    }

    /**
     * Creates the group and adds the current user + all selected members to GroupUser.
     */
    fun createGroup(onSuccess: () -> Unit) {
        val groupName = state.groupNameState.text.toString().trim()
        if (groupName.isBlank() || state.members.isEmpty() || isCreating) return

        viewModelScope.launch {
            isCreating = true
            errorMessage = null

            val currentUid = Database.currentUser?.uid
            if (currentUid == null) {
                errorMessage = "You must be signed in to create a group."
                isCreating = false
                return@launch
            }

            Database.createGroup(groupName)
                .onSuccess { groupId ->
                    val allUserIds = (state.members.map { it.username } + currentUid).distinct()
                    val addResults = allUserIds.map { uid ->
                        Database.addUserToGroup(groupId = groupId, userId = uid)
                    }

                    val failed = addResults.firstOrNull { it.isFailure }
                    if (failed != null) {
                        errorMessage = failed.exceptionOrNull()?.message ?: "Failed to add some members"
                    } else {
                        onSuccess()
                    }
                }
                .onFailure { error ->
                    errorMessage = error.message ?: "Failed to create group"
                }

            isCreating = false
        }
    }
}
