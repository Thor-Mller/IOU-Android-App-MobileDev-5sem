package com.freaky.iou.ui.group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freaky.iou.ui.data.models.Expense
import com.freaky.iou.ui.helpers.toKrString
import com.freaky.iou.ui.theme.IOUTheme

private enum class ExpenseTab(val title: String) {
    ACTIVE("Active"), ARCHIVED("Archived")
}

@Composable
fun GroupScreen(
    viewModel: GroupViewModel,
    onOpenSummary: () -> Unit = {},
    onAddExpense: () -> Unit = {},
    onExpenseClick: (expense: Expense, shareAmount: Int) -> Unit = { _, _ -> }
) {
    val state = viewModel.groupState
    var selectedTab by rememberSaveable { mutableStateOf(ExpenseTab.ACTIVE) }

    val totalGroupExpenses = remember(state.activeExpenses, state.archivedExpenses) {
        state.activeExpenses.sumOf { it.amount } + state.archivedExpenses.sumOf { it.amount }
    }
    val totalUserMustPay = remember(state.userShares) {
        state.userShares.values.sum()
    }

    val displayedExpenses = remember(selectedTab, state.activeExpenses, state.archivedExpenses) {
        when (selectedTab) {
            ExpenseTab.ACTIVE -> state.activeExpenses.sortedByDescending { it.amount - it.payed }
            ExpenseTab.ARCHIVED -> state.archivedExpenses
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.refresh()
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
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = state.group?.name?.ifBlank { "Group" } ?: "Group",
                        style = MaterialTheme.typography.titleLarge
                    )
                    state.group?.creator?.username?.let { creator ->
                        Text(
                            text = "Created by $creator",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                OutlinedButton(onClick = onOpenSummary) {
                    Text("Group Summary")
                }
            }

            // Middle content takes all remaining space
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    state.error != null -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = state.error,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(onClick = { viewModel.refresh() }) {
                                Text("Retry")
                            }
                        }
                    }

                    else -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Group & User Summary Card
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            text = "Your Total Share",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                        Text(
                                            text = totalUserMustPay.toKrString(),
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "Total Group Spend",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                        Text(
                                            text = totalGroupExpenses.toKrString(),
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                            }

                            // Active / Archived Tabs
                            PrimaryTabRow(selectedTabIndex = selectedTab.ordinal) {
                                ExpenseTab.entries.forEach { tab ->
                                    val count = when (tab) {
                                        ExpenseTab.ACTIVE -> state.activeExpenses.size
                                        ExpenseTab.ARCHIVED -> state.archivedExpenses.size
                                    }
                                    Tab(
                                        selected = selectedTab == tab,
                                        onClick = { selectedTab = tab },
                                        text = { Text("${tab.title} ($count)") }
                                    )
                                }
                            }

                            // Expense List for Selected Tab
                            if (displayedExpenses.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .padding(horizontal = 16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = when (selectedTab) {
                                            ExpenseTab.ACTIVE -> "No active expenses. Tap + to add one."
                                            ExpenseTab.ARCHIVED -> "No archived expenses yet."
                                        },
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            } else {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    contentPadding = PaddingValues(
                                        horizontal = 16.dp,
                                        vertical = 12.dp
                                    ),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(
                                        items = displayedExpenses,
                                        key = { it.id }
                                    ) { expense ->
                                        val userShare = state.userShares[expense]
                                            ?: state.userShares.entries.find { it.key.id == expense.id }?.value
                                            ?: 0

                                        ExpenseCard(
                                            expense = expense,
                                            userShareAmount = userShare,
                                            isArchived = selectedTab == ExpenseTab.ARCHIVED,
                                            onClick = { onExpenseClick(expense, userShare) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Bottom Add Button
            IconButton(
                onClick = onAddExpense,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add expense")
            }
        }
    }
}


@Composable
private fun ExpenseCard(
    expense: Expense, userShareAmount: Int, isArchived: Boolean, onClick: () -> Unit
) {
    val canPay = !isArchived && userShareAmount > 0

    Card(
        onClick = onClick,
        enabled = canPay,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = expense.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (expense.user.username.isNotBlank()) {
                    Text(
                        text = "Paid by ${expense.user.username}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Total: ${expense.amount.toKrString()}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                if (userShareAmount > 0) {
                    Text(
                        text = when {
                            isArchived -> "Settled"
                            else -> "You must pay"
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = when {
                        userShareAmount <= 0 -> "Paid"
                        else -> userShareAmount.toKrString()
                    },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (canPay) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun GroupScreenPreview() {
    IOUTheme() {
        GroupScreen(viewModel())
    }
}