package com.freaky.iou.ui.payment

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState

data class PaymentState @OptIn(ExperimentalMaterial3Api::class) constructor(
    var sliderState: SliderState = SliderState(),
    var amountState: TextFieldState = TextFieldState(),
    val initialAmountState: TextFieldState = TextFieldState()
)