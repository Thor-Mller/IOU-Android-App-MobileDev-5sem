package com.freaky.iou.ui.auth.signup

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freaky.iou.ui.auth.components.PasswordError
import com.freaky.iou.ui.data.Database
import com.freaky.iou.ui.data.models.User
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {

    var state by mutableStateOf(SignupState())
        private set

    init {
        viewModelScope.launch {
            snapshotFlow {
                listOf(
                    state.usernameState.text,
                    state.emailState.text,
                    state.passwordState.text,
                    state.confirmPasswordState.text
                )
            }.drop(1).collect {
                if (state.error.isNotEmpty()) {
                    state = state.copy(error = "")
                }
            }
        }
    }

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

    val passwordError: PasswordError? by derivedStateOf {
        when {
            state.passwordState.text.isBlank() -> null
            state.passwordState.text.length < 6 -> PasswordError.TooShort
            else -> null
        }
    }


    suspend fun onSignupClicked(onSuccess: () -> Unit) {
        if (!isFormValid) return

        val username = state.usernameState.text.toString()
        val email = state.emailState.text.toString()
        val password = state.passwordState.text.toString()


        val user = User(
            username = username,
            email = email,
            password = password,
        )

        state.usernameState.clearText()
        state.emailState.clearText()
        state.passwordState.clearText()
        state.confirmPasswordState.clearText()

        if (Database.signUp(user).isFailure) {
            state = state.copy(error = "Email/Username already in use!")
            return
        }

        onSuccess()
    }

}