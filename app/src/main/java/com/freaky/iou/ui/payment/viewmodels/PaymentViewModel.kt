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
import com.freaky.iou.ui.helpers.parseToCents
import com.freaky.iou.ui.helpers.toKrString
import com.freaky.iou.ui.payment.states.PaymentState
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
class PaymentViewModel(
    private val expenseId: UUID,
    payAmount: Int
) : ViewModel() {

    private val maxAmountCents: Int = payAmount.coerceAtLeast(0)
    private var lastTextDrivenSliderCents: Int? = null

    var state: PaymentState by mutableStateOf(
        PaymentState(
            sliderState = SliderState(
                value = maxAmountCents.toFloat(),
                steps = 9,
                valueRange = 0f..maxAmountCents.toFloat()
            ),
            amountState = TextFieldState(
                initialText = maxAmountCents.toKrString(showKr = false)
            )
        )
    )
        private set

    fun onSliderChange(): Boolean {
        val cents = state.sliderState.value.roundToInt().coerceIn(0, maxAmountCents)
        if (cents == lastTextDrivenSliderCents) {
            lastTextDrivenSliderCents = null
            return false
        }
        lastTextDrivenSliderCents = null

        val formatted = cents.toKrString(showKr = false)
        if (state.amountState.text.toString() != formatted) {
            state.amountState.edit {
                replace(0, length, formatted)
            }
        }
        return true
    }

    fun onNumberFieldChange() {
        val text = state.amountState.text.toString().trim().replace(',', '.')
        if (text.isEmpty() || text.endsWith('.')) return

        val cents = parseToCents(text) ?: return
        val previousSnapped = state.sliderState.value.roundToInt()

        state.sliderState.value = cents.toFloat()

        val newSnapped = state.sliderState.value.roundToInt()
        if (newSnapped != previousSnapped) {
            lastTextDrivenSliderCents = newSnapped
        }
    }

    fun onPay(onSuccess: () -> Unit = {}) {
        if (state.isLoading) return

        val rawText = state.amountState.text.toString()
        val amountCents = (parseToCents(rawText) ?: state.sliderState.value.roundToInt())
            .coerceIn(0, maxAmountCents)

        if (amountCents <= 0) {
            state = state.copy(error = "Payment amount must be greater than 0.")
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            Database.addPaymentToExpense(
                expenseId = expenseId,
                paymentAmount = amountCents
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
}
