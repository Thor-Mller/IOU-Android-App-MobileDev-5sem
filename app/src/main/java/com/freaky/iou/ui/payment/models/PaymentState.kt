package com.freaky.iou.ui.payment.models

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState
import java.util.UUID

data class PaymentState @OptIn(ExperimentalMaterial3Api::class) constructor(
    var sliderState: SliderState = SliderState(),
    var amountState: TextFieldState = TextFieldState(),
    val initialAmountState: TextFieldState = TextFieldState(),
    val expenseId: UUID = UUID.randomUUID(),
    val isLoading: Boolean = false,
    val error: String? = null
)