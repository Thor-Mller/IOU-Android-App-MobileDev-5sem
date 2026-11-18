package com.freaky.iou.ui.newgroup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freaky.iou.ui.newgroup.components.MemberCard
import com.freaky.iou.ui.theme.IOUTheme

@Composable
fun NewGroupScreen(
    viewModel: NewGroupViewModel = viewModel(),
    onCancel: () -> Unit = {},
    onGroupCreated: () -> Unit = {},
) {
    val state = viewModel.state

    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Creating a new group", style = MaterialTheme.typography.titleLarge)

            /*
            Button(onClick = {
                scope.launch {
                    Database.signIn(User(email = "debtor@test.dk", password = "password123"))
                }
            }) { Text("Log in as debtor@test") }
             */

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                state = state.groupNameState,
                label = { Text("Group Name") },
                modifier = Modifier.fillMaxWidth(),
                lineLimits = TextFieldLineLimits.SingleLine
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                state = state.usernameState,
                label = { Text("Add group member") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (viewModel.isSearching) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp), strokeWidth = 2.dp
                        )
                    }
                },
                onKeyboardAction = {
                    viewModel.searchResults.firstOrNull()?.let { firstMatch ->
                        viewModel.onAddMember(firstMatch)
                    }
                },
                lineLimits = TextFieldLineLimits.SingleLine
            )

            // Search Results Dropdown
            if (viewModel.searchResults.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    Column {
                        viewModel.searchResults.forEach { user ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.onAddMember(user) }
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(user.username, style = MaterialTheme.typography.bodyLarge)
                                Text("Add", color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }

            // Error message display
            viewModel.errorMessage?.let { error ->
                Spacer(Modifier.height(8.dp))
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = state.members, key = { it.id } // Better to key by unique user ID
                ) { member ->
                    MemberCard(member) {
                        viewModel.removeMember(member)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = onCancel, enabled = !viewModel.isCreating
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = { viewModel.createGroup(onSuccess = onGroupCreated) },
                    enabled = viewModel.isFormValid
                ) {
                    if (viewModel.isCreating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Create")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewGroupScreenPreview() {
    IOUTheme(darkTheme = false) {
        NewGroupScreen(viewModel = remember { NewGroupViewModel() })
    }
}