package com.freaky.iou.ui.newgroup

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freaky.iou.ui.data.Database
import com.freaky.iou.ui.data.models.User
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class NewGroupViewModel : ViewModel() {

    var state by mutableStateOf(NewGroupState())
        private set

    var searchResults by mutableStateOf<List<User>>(emptyList())
        private set

    var isSearching by mutableStateOf(false)
        private set

    var isCreating by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    val isFormValid: Boolean by derivedStateOf {
        state.groupNameState.text.isNotBlank() && state.members.isNotEmpty() && !isCreating
    }

    init {
        // Automatically observe TextFieldState changes with debounce
        viewModelScope.launch {
            snapshotFlow { state.usernameState.text.toString() }.debounce(300.milliseconds)
                .distinctUntilChanged().collectLatest { query ->
                    searchUsers(query)
                }
        }
    }

    private suspend fun searchUsers(query: String) {
        val trimmed = query.trim()
        if (trimmed.length < 2) {
            searchResults = emptyList()
            isSearching = false
            return
        }

        isSearching = true
        errorMessage = null

        Database.searchUsersByUsername(trimmed).onSuccess { users ->
            val existingIds = state.members.map { it.id }.toSet()
            val currentUid = Database.currentUser?.uid
            searchResults = users.filter { it.id !in existingIds && it.id != currentUid }
        }.onFailure { error ->
            errorMessage = error.message ?: "Failed to search users"
            searchResults = emptyList()
        }

        isSearching = false
    }

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

            Database.createGroup(groupName).onSuccess { groupId ->
                // Do NOT add currentUid here — CreateGroup already inserts you!
                val otherUserIds =
                    state.members.map { it.id }.filter { it != currentUid }.distinct()

                val addResults = otherUserIds.map { uid ->
                    Database.addUserToGroup(groupId = groupId, userId = uid)
                }

                val failed = addResults.firstOrNull { it.isFailure }
                if (failed != null) {
                    errorMessage = failed.exceptionOrNull()?.message ?: "Failed to add some members"
                } else {
                    onSuccess()
                }
            }.onFailure { error ->
                errorMessage = error.message ?: "Failed to create group"
            }

            isCreating = false
        }
    }
}