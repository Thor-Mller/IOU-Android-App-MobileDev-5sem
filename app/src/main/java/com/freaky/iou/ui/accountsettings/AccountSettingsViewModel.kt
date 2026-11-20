package com.freaky.iou.ui.accountsettings

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freaky.iou.ui.data.Database
import com.freaky.iou.ui.data.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class AccountSettingsViewModel : ViewModel() {

    var user: User? = User()

    var state: AccountSettings by mutableStateOf(AccountSettings())
        private set

    fun onSaveClicked() {
        viewModelScope.launch {
            Database.createUserRecord(
                state.username.text.toString(),
                state.phone.text.toString(),
                state.email.text.toString()
            )
        }
    }

    fun refresh() {
        viewModelScope.launch {
            val uid = Firebase.auth.currentUser?.uid ?: ""
            user = Database.getUser(uid).getOrElse { User() }

            if (user != null) {

                state = state.copy(
                    username = TextFieldState(user!!.username),
                    email = TextFieldState(user!!.email)
                )
            }
        }
    }

    fun onLogOutClicked(goToLogin: () -> Unit) {
        Database.signOut()
        goToLogin()
    }

    suspend fun onDeleteAccountClicked(goToLogin: () -> Unit) {
        Database.deleteCurrentUser()
        goToLogin()
    }
}