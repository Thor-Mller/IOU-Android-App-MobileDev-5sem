package com.freaky.iou.ui.auth.login

import android.util.Log
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.freaky.iou.ui.data.Database
import com.freaky.iou.ui.data.models.User

class LoginViewModel : ViewModel() {
    var state: LoginState by mutableStateOf(LoginState())
        private set

    val isFormValid: Boolean by derivedStateOf {
        state.emailState.text.isNotBlank() && state.passwordState.text.isNotBlank()
    }

    suspend fun onLoginClicked(onSuccess: (String) -> Unit) {

        if (!isFormValid) return

        // TODO: check credentials, send to homepage

        val email = state.emailState.text.toString()
        val password = state.passwordState.text.toString()

        val user = User(
            email = email,
            password = password
        )
        if (Database.signIn(user).isFailure){
            // println(Database.signIn(user))
            return
        }
        state.emailState.clearText()
        state.passwordState.clearText()

        onSuccess(email)



    }
}