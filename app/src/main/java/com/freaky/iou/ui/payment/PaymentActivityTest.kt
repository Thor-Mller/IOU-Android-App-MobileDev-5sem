package com.freaky.iou.ui.payment

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freaky.iou.ui.data.Database
import com.freaky.iou.ui.data.models.User
import com.freaky.iou.ui.expense.NewExpenseViewModel
import com.freaky.iou.ui.payment.views.NewExpenseScreen
import com.freaky.iou.ui.theme.IOUTheme
import java.util.UUID
import kotlinx.coroutines.launch

class PaymentActivityTest : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            IOUTheme {
                val context = LocalContext.current
                val scope = rememberCoroutineScope()
                var showNewExpenseScreen by remember { mutableStateOf(false) }
                var testGroupId by remember { mutableStateOf<UUID?>(null) }
                var statusText by remember {
                    mutableStateOf("Signed in as: ${Database.currentUser?.uid ?: "Not signed in"}")
                }


                if (showNewExpenseScreen && testGroupId != null) {
                    val expenseViewModel: NewExpenseViewModel = viewModel(key = testGroupId.toString()) {
                        NewExpenseViewModel(initialGroupId = testGroupId!!)
                    }

                    NewExpenseScreen(
                        viewModel = expenseViewModel,
                        onCancel = {
                            showNewExpenseScreen = false
                        },
                        onCreate = {
                            Toast.makeText(context, "Expense saved to DB!", Toast.LENGTH_SHORT).show()
                            showNewExpenseScreen = false
                        }
                    )
                } else {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = statusText)

                            Spacer(modifier = Modifier.height(16.dp))

                            // 1. Creates/signs in test users, creates a group, and adds both users
                            OutlinedButton(onClick = {
                                scope.launch {
                                    val user1 = User(
                                        email = "debtor@test.dk",
                                        password = "password123",
                                        username = "TestDebtor",
                                        phone = "11223344"
                                    )
                                    val user2 = User(
                                        email = "creator@test.dk",
                                        password = "password123",
                                        username = "TestCreator",
                                        phone = "55667788"
                                    )

                                    // Sign up or fall back to sign in so we get both UIDs
                                    Database.signUp(user1)
                                    Database.signIn(user1)
                                    val user1Uid = Database.currentUser?.uid

                                    Database.signUp(user2)
                                    Database.signIn(user2)
                                    val user2Uid = Database.currentUser?.uid

                                    // Sign back in as user1 before creating the group
                                    Database.signIn(user1)

                                    if (user1Uid != null && user2Uid != null) {
                                        Database.createGroup("Super Test Group")
                                            .onSuccess { groupId ->
                                                testGroupId = groupId
                                                Database.addUserToGroup(groupId, user1Uid)
                                                Database.addUserToGroup(groupId, user2Uid)

                                                statusText = "Group ready ($groupId)\nSigned in as: ${user1.username}"
                                                Log.d("IOU_TEST", "Created group $groupId with $user1Uid and $user2Uid")
                                            }
                                            .onFailure { e ->
                                                statusText = "Failed to create group: ${e.message}"
                                                Log.e("IOU_TEST", "Group creation failed", e)
                                            }
                                    } else {
                                        statusText = "Failed to resolve test user UIDs"
                                    }
                                }
                            }) {
                                Text("1. Seed Users & Create Group")
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // 2. Routes directly to NewExpenseScreen once the group is ready
                            Button(
                                onClick = { showNewExpenseScreen = true },
                                enabled = testGroupId != null
                            ) {
                                Text("2. Open New Expense Screen")
                            }
                        }
                    }
                }
            }
        }
    }
}