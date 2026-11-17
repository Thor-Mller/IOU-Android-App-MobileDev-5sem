package com.freaky.iou.ui.group.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.freaky.iou.ui.group.viewmodels.GroupMember
import com.freaky.iou.ui.group.viewmodels.NewGroupState
import com.freaky.iou.ui.group.viewmodels.NewGroupViewModel
import com.freaky.iou.ui.theme.IOUTheme

private val BoxShape = RoundedCornerShape(6.dp)
private val BorderWidth = 1.5.dp

/**
 * Screen for creating a new group (design: "New Group Screen S5"). UI only –
 * [onCreate] hands the finished state to the caller, which is responsible for
 * persisting it and sending the invites.
 */
@Composable
fun NewGroupScreen(
    viewModel: NewGroupViewModel,
    onCancel: () -> Unit = {},
    onCreate: (NewGroupState) -> Unit = {},
) {
    val state = viewModel.uiState
    val borderColor = MaterialTheme.colorScheme.onSurface

    val nameState = rememberTextFieldState(state.name)
    val usernameState = rememberTextFieldState()

    LaunchedEffect(nameState) {
        snapshotFlow { nameState.text.toString() }
            .collect { viewModel.onNameChange(it) }
    }

    fun submitMember() {
        if (viewModel.addMember(usernameState.text.toString())) {
            usernameState.clearText()
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
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 24.dp),
        ) {
            Spacer(Modifier.height(32.dp))

            // 1. Group name – centered, bold, bordered box
            BasicTextField(
                state = nameState,
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderWidth, borderColor, BoxShape)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                decorator = { innerTextField ->
                    Box(contentAlignment = Alignment.Center) {
                        if (nameState.text.isEmpty()) {
                            Text(
                                text = "Group Name",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(Modifier.height(64.dp))

            // 2. Add / Remove Members
            Text(
                text = "Add / Remove Members",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                state = usernameState,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Username to add...", style = MaterialTheme.typography.titleLarge)
                },
                textStyle = MaterialTheme.typography.titleLarge,
                lineLimits = TextFieldLineLimits.SingleLine,
                shape = BoxShape,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                onKeyboardAction = { submitMember() }
            )

            Spacer(Modifier.height(16.dp))

            // 3. Member list – fills the remaining space
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(BorderWidth, borderColor, BoxShape),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.members, key = { it.username }) { member ->
                    MemberCard(
                        member = member,
                        onRemove = { viewModel.removeMember(member) }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // 4. Cancel / Confirm
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WireframeButton(text = "Cancel", onClick = onCancel)
                WireframeButton(
                    text = "Confirm",
                    onClick = { onCreate(state) },
                    enabled = state.canCreate
                )
            }
        }
    }
}

@Composable
private fun MemberCard(member: GroupMember, onRemove: () -> Unit) {
    val borderColor = MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .heightIn(min = 56.dp)
            .border(BorderWidth, borderColor, BoxShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = member.username,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (member.isPending) {
                Text(
                    text = "Pending Invite...",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Trash button in its own bordered square, like the design
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .border(BorderWidth, borderColor, BoxShape),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove ${member.username}",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
private fun WireframeButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = BoxShape,
        border = BorderStroke(BorderWidth, MaterialTheme.colorScheme.onSurface),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewGroupScreenPreview() {
    IOUTheme(darkTheme = false) {
        val mockViewModel = remember {
            NewGroupViewModel(
                initialMembers = listOf(
                    GroupMember("Person 1", isPending = true),
                    GroupMember("Person 2", isPending = false),
                    GroupMember("Person 3", isPending = true),
                )
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
