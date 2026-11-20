package com.freaky.iou.ui.home

import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freaky.iou.ui.data.Database
import com.freaky.iou.ui.newgroup.NewGroupState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        loadGroups()
    }

    fun loadGroups() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            Database.getCurrentGroups()
                .onSuccess { groups ->
                    state = state.copy(groups = groups, isLoading = false)
                }
                .onFailure { throwable ->
                    state = state.copy(error = throwable.message, isLoading = false)
                }
        }
    }
}