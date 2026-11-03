package com.freaky.iou.ui.auth.signup

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.freaky.iou.ui.auth.components.PasswordError

class SignupViewModel {

    var state: SignupState by mutableStateOf(SignupState())
        private set

    val isFormValid: Boolean by derivedStateOf {
        state.usernameState.text.isNotBlank() && state.emailState.text.isNotBlank() && isConfirmPasswordValid
    }

    val isConfirmPasswordValid: Boolean by derivedStateOf {
        state.passwordState.text.isNotBlank() && state.confirmPasswordState.text.isNotBlank() && (state.passwordState.text == state.confirmPasswordState.text)
    }

    val confirmPasswordError: PasswordError? by derivedStateOf {
        when {
            state.confirmPasswordState.text.isBlank() -> null
            !isConfirmPasswordValid -> PasswordError.NonMatching
            else -> null
        }
    }

    fun onSignupClicked() {
        if (!isFormValid) return

        // TODO: save user credentials, send to login page

        state.usernameState.clearText()
        state.emailState.clearText()
        state.passwordState.clearText()
        state.confirmPasswordState.clearText()
    }

}