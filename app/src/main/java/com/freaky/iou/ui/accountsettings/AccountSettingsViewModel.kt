package com.freaky.iou.ui.accountsettings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.freaky.iou.ui.data.Database

class AccountSettingsViewModel : ViewModel() {

    var state: AccountSettings by mutableStateOf(
        AccountSettings(
            username = "", phone = "", email = ""
        )
    )

    fun onLogOutClicked(goToLogin: () -> Unit) {
        Database.signOut()
        goToLogin()
    }

    suspend fun onDeleteAccountClicked(goToLogin: () -> Unit) {
        Database.deleteCurrentUser()
        goToLogin()
    }
}