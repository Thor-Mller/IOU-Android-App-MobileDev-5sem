package com.freaky.iou.ui.reauth

import androidx.lifecycle.ViewModel
import com.freaky.iou.ui.data.Database

class ReAuthVeiwModel : ViewModel() {
    suspend fun onLogout(onSuccess: () -> Unit) {
        Database.signOut()

        onSuccess()
    }
}