package com.freaky.iou.ui.payment

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
class PaymentViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val initialExpense: Float = savedStateHandle.get<Float>("initialAmount") ?: 0f

    var state: PaymentState by mutableStateOf(
        PaymentState(
            sliderState = SliderState(
                value = initialExpense, valueRange = 0f..initialExpense
            ), amountState = TextFieldState(
                initialText = initialExpense.toString()
            )
        )
    )

    fun onSliderChange() {
        val rawValue = state.sliderState.value
        val formatted = formatAmount(rawValue)

        // Only update text field if the formatted text actually differs
        if (state.amountState.text.toString() != formatted) {
            state.amountState.edit {
                replace(0, length, formatted)
            }
        }
    }

    fun onNumberFieldChange() {
        val text = state.amountState.text.toString().trim().replace(',', '.')

        // Don't update slider if input is empty or trailing decimal point
        if (text.isEmpty() || text.endsWith('.')) return

        val parsed = text.toFloatOrNull() ?: return
        val clamped = parsed.coerceIn(
            state.sliderState.valueRange.start,
            state.sliderState.valueRange.endInclusive
        )

        if (state.sliderState.value != clamped) {
            state.sliderState.value = clamped
        }
    }

    private fun formatAmount(value: Float): String {
        val rounded = (value * 100f).roundToInt() / 100f
        return if (rounded % 1f == 0f) {
            rounded.toInt().toString()
        } else {
            rounded.toString()
        }
    }
}