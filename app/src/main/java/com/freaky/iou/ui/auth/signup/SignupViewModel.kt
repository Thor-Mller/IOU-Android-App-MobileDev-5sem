package com.freaky.iou.ui.auth.signup

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.freaky.iou.ui.auth.components.PasswordError
import com.freaky.iou.ui.data.Database
import com.freaky.iou.ui.data.models.User

class SignupViewModel : ViewModel() {

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

    suspend fun onSignupClicked(onSuccess: () -> Unit) {
        if (!isFormValid) return

        // TODO: save user credentials
        val username = state.usernameState.text.toString()
        val email = state.emailState.text.toString()
        val password = state.passwordState.text.toString()


        val user = User(
            username = username,
            email = email,
            password = password,
        )

        if (Database.signUp(user).isFailure){
            return
        }

        state.usernameState.clearText()
        state.emailState.clearText()
        state.passwordState.clearText()
        state.confirmPasswordState.clearText()

        onSuccess()
    }

}