package com.freaky.iou.ui.accountsettings.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.freaky.iou.ui.accountsettings.components.ButtonField
import com.freaky.iou.ui.accountsettings.components.DropdownField
import com.freaky.iou.ui.accountsettings.components.EditField
import com.freaky.iou.ui.accountsettings.viewmodels.AccountSettingsViewModel
import com.freaky.iou.ui.theme.IOUTheme

@Composable
fun NewAccountSettingsView(
    viewModel: AccountSettingsViewModel
){
    val state = viewModel.state

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .verticalScroll(rememberScrollState())
                .padding(24.dp, 40.dp, 24.dp, 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Account Settings", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            EditField("Username", state.username)
            EditField("Phone number", state.phone)
            EditField("Email", state.email)
            DropdownField("Currency", "DKK")
            ButtonField("Save", {})

            Spacer(modifier = Modifier.weight(1f))

            ButtonField("Log Out", {})
            ButtonField("Delete Account", {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewAccountSettingsView() {
    IOUTheme {
        val previewViewModel = remember {
            AccountSettingsViewModel(
            )
        }
        NewAccountSettingsView(previewViewModel)
    }
}