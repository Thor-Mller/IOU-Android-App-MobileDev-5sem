package com.freaky.iou

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.iou.data.ExpenseRepository
import com.freaky.iou.ui.theme.IOUTheme
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IOUTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TestButton()
                    }
                }
            }
        }
    }
}

suspend fun buttonClick() {
    try {
        val repo = ExpenseRepository()
        val date = Date.from(java.time.Instant.now())
        repo.upsertUser(
            userId = "test-user-2", username = "user$date", phone = "10101010"
        )
        Log.d("IOU", "User upserted successfully!")
    } catch (e: Exception) {
        Log.e("IOU", "Error: ${e.message}", e)
    }
}

@Composable
fun TestButton() {
    val scope = rememberCoroutineScope()

    Button(onClick = {
        scope.launch {
            buttonClick()
        }
    }) {
        Text("ADD USER!")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IOUTheme {}
}