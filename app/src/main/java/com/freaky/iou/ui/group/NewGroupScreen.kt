package com.freaky.iou.ui.group

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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freaky.iou.ui.data.Database
import com.freaky.iou.ui.data.models.User
import com.freaky.iou.ui.group.components.MemberCard
import com.freaky.iou.ui.theme.IOUTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun NewGroupScreen(
    viewModel: NewGroupViewModel,
    onCancel: () -> Unit = {},
    onCreate: (NewGroupState) -> Unit = {},
) {
    val state = viewModel.state

    // Inside your Composable (or move into your ViewModel):
    var searchResults by remember {
        mutableStateOf<List<User>>(
            emptyList()
        )
    }

    LaunchedEffect(state.usernameState.text) {
        val query = state.usernameState.text.toString().trim()
        if (query.length < 2) { // Wait for at least 2 characters
            searchResults = emptyList()
            return@LaunchedEffect
        }

        delay(300.milliseconds) // Debounce 300ms while typing
        Database.searchUsersByUsername(query)
            .onSuccess { users ->
                val existingIds = state.members.map { it.id }.toSet()
                searchResults = users.filter {
                    it.id !in existingIds && it.id != Database.currentUser?.uid
                }.map { User(it.id, it.username, it.email, it.phone) }
            }
    }

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


            Spacer(Modifier.height(16.dp))


            OutlinedTextField(
                state = state.groupNameState,
                label = { Text("Group Name") },
                modifier = Modifier.fillMaxWidth(),
                lineLimits = TextFieldLineLimits.SingleLine
            )


            Spacer(Modifier.height(32.dp))


            OutlinedTextField(
                state = state.usernameState,
                label = { Text("Add group member") },
                modifier = Modifier.fillMaxWidth(),
                onKeyboardAction = {
                    // Optional: Pressing Enter selects the top match if one exists
                    searchResults.firstOrNull()?.let { firstMatch ->
                        viewModel.onAddMember(firstMatch)
                        state.usernameState.clearText()
                        searchResults = emptyList()
                    }
                },
                lineLimits = TextFieldLineLimits.SingleLine
            )

            // Search Results Dropdown / List
            if (searchResults.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    Column {
                        searchResults.forEach { user ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.onAddMember(user)
                                        state.usernameState.clearText()
                                        searchResults = emptyList()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(user.username, style = MaterialTheme.typography.bodyLarge)
                                Text("Add", color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }


            Spacer(Modifier.height(16.dp))


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = state.members, key = { it.username }) { member ->
                    MemberCard(member) {
                        viewModel.removeMember(member)
                    }
                }
            }


            Spacer(Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = onCancel) {
                    Text("Cancel")
                }
                OutlinedButton(
                    onClick = { onCreate(state) }, enabled = viewModel.isFormValid
                ) {
                    Text("Create")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewGroupScreenPreview() {
    IOUTheme(darkTheme = false) {
        val mockViewModel = remember {
            NewGroupViewModel(
            )
        }
        NewGroupScreen(viewModel = mockViewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewGroupScreenEmptyPreview() {
    IOUTheme(darkTheme = true) {
        NewGroupScreen(viewModel = remember { NewGroupViewModel() })
    }
}
