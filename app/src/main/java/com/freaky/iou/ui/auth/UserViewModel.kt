package com.freaky.iou.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class User(val username: String)

class UserViewModel : ViewModel() {
    var currentUser by mutableStateOf<User?>(null)
        private set

    fun onUserAuthenticated(username: String) {
        currentUser = User(username = username)
    }

    fun logout() {
        currentUser = null
    }
}