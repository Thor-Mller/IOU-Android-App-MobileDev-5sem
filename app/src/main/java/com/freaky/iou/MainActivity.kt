package com.freaky.iou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.freaky.iou.ui.accountsettings.viewmodels.AccountSettingsViewModel
import com.freaky.iou.ui.accountsettings.views.NewAccountSettingsView
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.freaky.iou.ui.navigation.AppNavigation
import com.freaky.iou.ui.theme.IOUTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IOUTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val previewViewModel = remember {
                        AccountSettingsViewModel(
                        )
                    }
                    NewAccountSettingsView(previewViewModel)
                    /*Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}
