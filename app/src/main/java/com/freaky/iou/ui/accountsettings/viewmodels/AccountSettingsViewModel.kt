package com.freaky.iou.ui.accountsettings.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.freaky.iou.ui.accountsettings.models.AccountSettings

class AccountSettingsViewModel () : ViewModel() {
    var state: AccountSettings by mutableStateOf(
        AccountSettings(
            username = "",
            phone = "",
            email = ""
        )
    )
}