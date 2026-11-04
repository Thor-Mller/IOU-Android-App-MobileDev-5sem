package com.freaky.iou.ui.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freaky.iou.ui.auth.components.EmailTextField
import com.freaky.iou.ui.auth.components.PasswordTextField
import com.freaky.iou.ui.auth.components.UsernameTextField
import com.freaky.iou.ui.theme.IOUTheme

@Composable
fun SignupScreen(viewModel: SignupViewModel) {
    val state = viewModel.state;

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "IOU",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Cursive
            )

            Box(
                modifier = Modifier
                    .aspectRatio(ratio = 0.6f)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    UsernameTextField(state.usernameState, required = true)

                    EmailTextField(state = state.emailState, required = true)

                    PasswordTextField(state = state.passwordState, required = true)

                    PasswordTextField(
                        state = state.confirmPasswordState,
                        required = true,
                        prefix = "Confirm ",
                        error = viewModel.confirmPasswordError
                    )

                    Button(
                        onClick = { viewModel.onSignupClicked() }, enabled = viewModel.isFormValid
                    ) {
                        Text("Sign Up")
                    }
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Already have an account?")
                Button(
                    onClick = { /* TODO: send to login page */ }) {
                    Text("Log in!", textAlign = TextAlign.Center)
                }
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    IOUTheme(darkTheme = true) {
        SignupScreen(SignupViewModel())
    }
}