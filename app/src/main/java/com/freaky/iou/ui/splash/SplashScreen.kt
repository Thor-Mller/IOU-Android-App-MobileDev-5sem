package com.freaky.iou.ui.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    extraDelayMillis: Long = 500,
    onGoToAuth: () -> Unit = {},
    onGoToReAuth: () -> Unit = {}
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("Animation - 1.json")
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1 // Play once for the splash screen
    )

    val isFinished = composition != null && progress == 1f
    LaunchedEffect(isFinished) {
        if (isFinished) {
            delay(extraDelayMillis)
            if (FirebaseAuth.getInstance().currentUser != null) {
                onGoToReAuth()
            } else
                onGoToAuth()
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxSize()
        )
    }

}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF000000)
    ) {
        SplashScreen()
    }
}