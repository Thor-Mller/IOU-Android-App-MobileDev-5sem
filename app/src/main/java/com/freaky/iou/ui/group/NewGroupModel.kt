package com.freaky.iou.ui.group.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * A member shown on the "New Group" screen.
 *
 * [isPending] is true while the invite has not been accepted yet.
 * Members added from this screen start out as pending.
 */
data class GroupMember(
    val username: String,
    val isPending: Boolean = true,
)

/**
 * UI state for the "New Group" screen.
 *
 * UI only for now: members are plain usernames typed in by the user.
 * Once the backend has a Group table this should hold real User ids instead.
 */
data class NewGroupState(
    val name: String = "",
    val members: List<GroupMember> = emptyList(),
) {
    val canCreate: Boolean
        get() = name.isNotBlank() && members.isNotEmpty()
}

class NewGroupViewModel(
    initialName: String = "",
    initialMembers: List<GroupMember> = emptyList(),
) : ViewModel() {

    var uiState by mutableStateOf(NewGroupState(initialName, initialMembers))
        private set

    fun onNameChange(newName: String) {
        uiState = uiState.copy(name = newName)
    }

    /** Returns true if the member was added, false if blank or a duplicate. */
    fun addMember(username: String): Boolean {
        val trimmed = username.trim()
        if (trimmed.isEmpty()) return false
        if (uiState.members.any { it.username.equals(trimmed, ignoreCase = true) }) return false
        uiState = uiState.copy(members = uiState.members + GroupMember(trimmed))
        return true
    }

    fun removeMember(member: GroupMember) {
        uiState = uiState.copy(members = uiState.members - member)
    }
}
