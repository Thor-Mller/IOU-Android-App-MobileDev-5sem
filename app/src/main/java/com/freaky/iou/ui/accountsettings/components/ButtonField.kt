package com.freaky.iou.ui.accountsettings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freaky.iou.ui.theme.IOUTheme

@Composable
fun ButtonField(name: String, action: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(10.dp, 0.dp, 10.dp, 5.dp)
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    action()
                }
            ) {
                Text(name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonFieldField(){
    IOUTheme {
        ButtonField("Log out", {})
    }
}