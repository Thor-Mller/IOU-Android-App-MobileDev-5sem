package com.freaky.iou.ui.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freaky.iou.ui.auth.components.EmailTextField
import com.freaky.iou.ui.auth.components.PasswordTextField
import com.freaky.iou.ui.theme.IOUTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onNavigateToSignup: () -> Unit = {},
    onLoginSuccess: (String) -> Unit = {},
) {
    val state = viewModel.state

    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .verticalScroll(state = rememberScrollState())
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
                    .clip(RoundedCornerShape(10))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),

                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    EmailTextField(state = state.emailState, required = true)

                    PasswordTextField(state = state.passwordState, required = true)

                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.onLoginClicked { email ->
                                    onLoginSuccess(email)
                                }
                            }

                        }, enabled = viewModel.isFormValid
                    ) {
                        Text("Sign In")
                    }

                    if (state.error.isNotBlank()) {
                        Text(state.error, color = MaterialTheme.colorScheme.error)
                    }
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("No account?")
                Button(
                    onClick = onNavigateToSignup
                ) {
                    Text("Create one here!", textAlign = TextAlign.Center)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    IOUTheme(darkTheme = true) {
        LoginScreen()
    }
}