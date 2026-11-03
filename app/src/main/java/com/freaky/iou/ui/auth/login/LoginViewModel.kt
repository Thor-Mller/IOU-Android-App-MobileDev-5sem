package com.freaky.iou.ui.auth.login

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var state: LoginState by mutableStateOf(LoginState())
        private set

    val isFormValid: Boolean by derivedStateOf {
        state.usernameState.text.isNotBlank() && state.passwordState.text.isNotBlank()
    }

    fun onLoginClicked() {

        val username: String = state.usernameState.text.toString()
        val password: String = state.passwordState.text.toString()

        if (username.isBlank() or password.isBlank()) {
            return
        }

        // TODO: check credentials, reroute to homepage

        state.usernameState.clearText()
        state.passwordState.clearText()

    }
}