package com.freaky.iou.ui.payment.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.freaky.iou.ui.theme.IOUTheme

@Composable
fun NumberField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    allowDecimals: Boolean = true,
    label: String = "Amount",
) {
    OutlinedTextField(
        state = state,
        modifier = modifier,
        lineLimits = TextFieldLineLimits.SingleLine,
        label = {Text(label)},
        keyboardOptions = KeyboardOptions(
            keyboardType = if (allowDecimals) KeyboardType.Decimal else KeyboardType.Number
        ),
        inputTransformation = {
            if (!asCharSequence().all { it.isDigit() }) {
                revertAllChanges()
            }
        }
    )
}

@Preview
@Composable
fun NumberFieldPreview() {
    IOUTheme() {
        NumberField(TextFieldState("0"))
    }
}