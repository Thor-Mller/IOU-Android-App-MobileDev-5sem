package com.freaky.iou.ui.payment.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freaky.iou.ui.payment.components.NumberField
import com.freaky.iou.ui.payment.viewmodels.PaymentViewModel
import com.freaky.iou.ui.theme.IOUTheme
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel = viewModel(),
    onConfirm: (Float) -> Unit = {}
) {
    val state = viewModel.state

    // Live sync: when the user drags the slider, update the text field
    LaunchedEffect(state.sliderState) {
        snapshotFlow { state.sliderState.value }
            .distinctUntilChanged()
            .collect {
                viewModel.onSliderChange()
            }
    }

    // Live sync: when the user types, update the slider thumb
    LaunchedEffect(state.amountState) {
        snapshotFlow { state.amountState.text }
            .distinctUntilChanged()
            .collect {
                viewModel.onNumberFieldChange()
            }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "You owe ${state.sliderState.valueRange.endInclusive.toInt()} kr",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                NumberField(
                    state = state.amountState,
                    modifier = Modifier.fillMaxWidth()
                )

                Slider(
                    state = state.sliderState,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        val amount = state.amountState.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
                        onConfirm(amount)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("OK")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentScreenPreview() {
    IOUTheme(darkTheme = true) {
        val previewViewModel = remember {
            PaymentViewModel(
                savedStateHandle = SavedStateHandle(mapOf("initialAmount" to 200f))
            )
        }
        PaymentScreen(viewModel = previewViewModel)
    }
}