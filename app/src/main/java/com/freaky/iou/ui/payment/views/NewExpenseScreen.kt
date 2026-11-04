package com.freaky.iou.ui.payment.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import kotlin.math.roundToInt

@Composable
fun NewExpenseScreen(
    viewModel: NewExpenseViewModel,
    onCancel: () -> Unit = {},
    onCreate: (Expense) -> Unit = {}
) {
    val state = viewModel.uiState

    val titleState = rememberTextFieldState(state.title)
    val amountState = rememberTextFieldState(
        if (state.totalAmount > 0.0) state.totalAmount.roundToInt().toString() else ""
    )

    LaunchedEffect(titleState) {
        snapshotFlow { titleState.text.toString() }
            .collect { viewModel.onTitleChange(it) }
    }

    LaunchedEffect(amountState) {
        snapshotFlow { amountState.text.toString() }
            .collect { viewModel.onTotalAmountTextChange(it) }
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

            // Split Mode / Hint (matching mockup)
            Text(
                text = "Enter percentages for each member.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

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
                state.members.forEach { memberId ->
                    val percentage = state.memberPercentages[memberId] ?: 0f
                    val amountOwed = state.amountForMember(memberId)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Member Name / ID
                        Text(
                            text = memberId,
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
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 5. Actions (Cancel / Confirm)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                ) {
                    Text("Cancel", fontSize = 18.sp)
                }

                Button(
                    onClick = {
                        if (state.isSplitValid && state.title.isNotBlank()) {
                            onCreate(state)
                        }
                    },
                    enabled = state.isSplitValid && state.title.isNotBlank() && state.totalAmount > 0.0,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Confirm", fontSize = 18.sp)
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