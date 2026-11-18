package com.freaky.iou.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    extraDelayMillis: Long = 500,
    onGoToApp: () -> Unit = {}
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
            onGoToApp()
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