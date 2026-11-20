package com.freaky.iou.ui.auth.login

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
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var state: LoginState by mutableStateOf(LoginState())
        private set

    init {
        viewModelScope.launch {
            snapshotFlow {
                listOf(
                    state.emailState.text,
                    state.passwordState.text,
                )
            }.drop(1).collect {
                if (state.error.isNotEmpty()) {
                    state = state.copy(error = "")
                }
            }
        }
    }

    val isFormValid: Boolean by derivedStateOf {
        state.emailState.text.isNotBlank() && state.passwordState.text.isNotBlank()
    }

    suspend fun onLoginClicked(onSuccess: (String) -> Unit) {

        if (!isFormValid) return

        val email = state.emailState.text.toString()
        val password = state.passwordState.text.toString()

        val user = User(
            email = email, password = password
        )

        // Only clear password on wrong creds
        state.passwordState.clearText()

        if (Database.signIn(user).isFailure) {
            state = state.copy(error = "User not found!")
            return
        }

        state.emailState.clearText()

        onSuccess(email)

    }
}