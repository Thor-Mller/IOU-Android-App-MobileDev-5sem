package com.freaky.iou.ui.payment.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.freaky.iou.domain.model.Expense
import com.freaky.iou.ui.expense.NewExpenseViewModel
import com.freaky.iou.ui.payment.components.NumberField
import com.freaky.iou.ui.theme.IOUTheme
import java.util.UUID
import kotlin.math.roundToInt
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(FlowPreview::class)
@Composable
fun NewExpenseScreen(
    viewModel: NewExpenseViewModel,
    onCancel: () -> Unit = {},
    onCreate: (Expense) -> Unit = {}
) {
    val state = viewModel.uiState
    val searchPeopleState = rememberTextFieldState("")
    val titleState = rememberTextFieldState(state.title)
    val amountState = rememberTextFieldState(
        if (state.totalAmount > 0.0) state.totalAmount.roundToInt().toString() else ""
    )
    // Maps userId -> username so the split table displays names instead of raw UIDs
    val memberNames = remember { mutableStateMapOf<String, String>() }

    LaunchedEffect(titleState) {
        snapshotFlow { titleState.text.toString() }
            .collect { viewModel.onTitleChange(it) }
    }

    LaunchedEffect(amountState) {
        snapshotFlow { amountState.text.toString() }
            .collect { viewModel.onTotalAmountTextChange(it) }
    }

    // Debounced search for users in the group by username, email, or phone
    LaunchedEffect(viewModel.uiState.groupId, searchPeopleState) {
        snapshotFlow { searchPeopleState.text.toString() }
            .debounce(300)
            .distinctUntilChanged()
            .collect { query ->
                if (viewModel.selectedSearchUser?.username != query) {
                    viewModel.clearUserSearch()
                    viewModel.searchUsersInGroup(viewModel.uiState.groupId, query)
                }
            }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "New Expense",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            HorizontalDivider(thickness = 2.dp, color = Color.Black)

            // 1. Expense Name Input
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Expense name",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    state = titleState,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            // 2. Expense Amount Input
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Expense amount",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                NumberField(
                    state = amountState,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 3. Member Split Table Header
            Text(
                text = "Split between members",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            // Split Mode / Hint
            Text(
                text = "Enter percentages for each member.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            // Search People & Add Button (around line 155)
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        state = searchPeopleState,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        label = { Text("Add people to expense") },
                        lineLimits = TextFieldLineLimits.SingleLine
                    )
                    Button(
                        onClick = {
                            viewModel.selectedSearchUser?.let { selectedUser ->
                                viewModel.addDebtor(selectedUser.id, selectedUser.username)
                                searchPeopleState.clearText()
                                viewModel.clearUserSearch()
                            }
                        },
                        enabled = !viewModel.isSaving && viewModel.selectedSearchUser != null,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Add", fontSize = 16.sp)
                    }
                }

                // Matching Group Members Dropdown
                if (viewModel.searchResults.isNotEmpty()) {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column {
                            viewModel.searchResults.forEach { user ->
                                ListItem(
                                    headlineContent = { Text(user.username) },
                                    supportingContent = { Text("${user.email} • ${user.phone}") },
                                    modifier = Modifier.clickable {
                                        searchPeopleState.setTextAndPlaceCursorAtEnd(user.username)
                                        viewModel.selectSearchUser(user)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // 4. Split Table Box
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Table Columns Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Member",
                        modifier = Modifier.weight(1.5f),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "%",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "kr",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.End,
                        color = Color.DarkGray
                    )
                }

                // Member Rows
                state.getMembers().forEach { memberId ->
                    val percentage = state.memberPercentages[memberId] ?: 0f
                    val amountOwed = state.amountForMember(memberId)
                    val displayName = state.memberNamesToIds
                        .entries
                        .firstOrNull { it.value == memberId }
                        ?.key ?: memberId

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Member Username (falls back to memberId for pre-populated members)
                        Text(
                            text = displayName,
                            modifier = Modifier.weight(1.5f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        // % Input
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicTextField(
                                value = if (percentage == 0f) "" else percentage.toInt().toString(),
                                onValueChange = { input ->
                                    val parsed = input.filter { it.isDigit() }.toFloatOrNull() ?: 0f
                                    viewModel.onMemberPercentageChange(memberId, parsed)
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                textStyle = MaterialTheme.typography.bodyLarge.copy(
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Medium
                                ),
                                modifier = Modifier
                                    .width(64.dp)
                                    .border(1.5.dp, Color.Black, RoundedCornerShape(6.dp))
                                    .padding(vertical = 6.dp)
                            )
                        }

                        // Calculated kr
                        Text(
                            text = amountOwed.roundToInt().toString(),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        )
                        IconButton(
                            onClick = {
                                memberNames.remove(memberId)
                                viewModel.removeDebtor(memberId)
                            },
                            enabled = !viewModel.isSaving,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove $displayName",
                                tint = Color.DarkGray,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                HorizontalDivider(thickness = 1.5.dp, color = Color.Black)

                // Total Summary Row
                val totalPercent = state.memberPercentages.values.sum()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total",
                        modifier = Modifier.weight(1.5f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "${totalPercent.roundToInt()}%",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "${state.totalAmount.roundToInt()} kr",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.width(32.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            viewModel.errorMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 5. Actions (Cancel / Confirm)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    enabled = !viewModel.isSaving,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                ) {
                    Text("Cancel", fontSize = 18.sp)
                }

                Button(
                    onClick = {
                        viewModel.submitExpense(onSuccess = onCreate)
                    },
                    enabled = !viewModel.isSaving &&
                            state.isSplitValid &&
                            state.title.isNotBlank() &&
                            state.totalAmount > 0.0,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (viewModel.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .height(22.dp)
                                .width(22.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Confirm", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewExpenseScreenPreview() {
    IOUTheme(darkTheme = false) {
        val mockViewModel = remember {
            NewExpenseViewModel(
                initialTitle = "Groceries",
                initialAmount = 300.0,
                initialGroupId = UUID.randomUUID(),
                initialMembers = listOf("Person 1", "Person 2", "Person 3")
            ).apply {
                onMemberPercentageChange("Person 1", 50f)
                onMemberPercentageChange("Person 2", 30f)
                onMemberPercentageChange("Person 3", 20f)
            }
        }

        NewExpenseScreen(
            viewModel = mockViewModel,
            onCancel = {},
            onCreate = {}
        )
    }
}