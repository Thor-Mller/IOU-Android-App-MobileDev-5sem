package com.freaky.iou.ui.accountsettings.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.freaky.iou.ui.accountsettings.component.EditField
import com.freaky.iou.ui.theme.IOUTheme

@Composable
fun NewAccountSettingsView(){
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Text(text = "Account Settings", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        EditField("Profile", "Current Name")
        EditField("Username", "Current Name")
        EditField("Email", "name@example.com")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewAccountSettingsView() {
    IOUTheme {
        NewAccountSettingsView()
    }
}