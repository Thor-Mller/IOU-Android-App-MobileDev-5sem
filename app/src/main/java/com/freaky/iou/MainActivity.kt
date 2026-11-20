package com.freaky.iou

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import com.freaky.iou.ui.navigation.AppNavigation
import com.freaky.iou.ui.theme.IOUTheme

// FragmentActivity (instead of ComponentActivity) is required for BiometricPrompt
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IOUTheme {

            AppNavigation()

            }
        }
    }
}
