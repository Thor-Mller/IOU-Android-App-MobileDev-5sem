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

    fun onLoginClicked(onSuccess: (String) -> Unit) {

        if (!isFormValid) return

        // TODO: check credentials, send to homepage

        val username = state.usernameState.text.toString()

        state.usernameState.clearText()
        state.passwordState.clearText()

        onSuccess(username)
    }
}