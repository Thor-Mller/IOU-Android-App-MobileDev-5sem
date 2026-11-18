package com.freaky.iou.ui.payment.viewmodels

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freaky.iou.ui.data.Database
import com.freaky.iou.ui.payment.models.PaymentState
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
class PaymentViewModel(
    private val expenseId: UUID,
    payAmount: Float
) : ViewModel() {
    private val initialExpense: Float = payAmount

    var state: PaymentState by mutableStateOf(
        PaymentState(
            sliderState = SliderState(
                value = initialExpense,
                valueRange = 0f..initialExpense
            ),
            amountState = TextFieldState(
                initialText = formatAmount(initialExpense)
            )
        )
    )
        private set

    fun onSliderChange() {
        val rawValue = state.sliderState.value
        val formatted = formatAmount(rawValue)

        if (state.amountState.text.toString() != formatted) {
            state.amountState.edit {
                replace(0, length, formatted)
            }
        }
    }

    fun onNumberFieldChange() {
        val text = state.amountState.text.toString().trim().replace(',', '.')

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

    fun onPay(onSuccess: () -> Unit = {}) {
        val rawText = state.amountState.text.toString().trim().replace(',', '.')
        val parsedAmount = rawText.toFloatOrNull() ?: state.sliderState.value
        val roundedAmount = ((parsedAmount * 100f).roundToInt() / 100f)
            .coerceIn(0f, initialExpense)

        if (roundedAmount <= 0f) {
            state = state.copy(error = "Payment amount must be greater than 0.")
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            Database.addPaymentToExpense(
                expenseId = expenseId,
                paymentAmount = roundedAmount
            )
                .onSuccess {
                    state = state.copy(isLoading = false)
                    onSuccess()
                }
                .onFailure { throwable ->
                    state = state.copy(
                        isLoading = false,
                        error = throwable.message ?: "Payment failed."
                    )
                }
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