package com.example.purpleapex.driver.presentation.driver_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purpleapex.driver.domain.network.DriverClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DriverListViewModel(
    private val driverClient: DriverClient
) : ViewModel() {
    private val _state = MutableStateFlow(DriverListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            _state.update {
                it.copy(
                    drivers = driverClient.getDrivers(year = 2025),
                    isLoading = false,
                )
            }
        }
    }

    fun onAction(action: DriverListAction) {
        when (action) {
            is DriverListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is DriverListAction.OnDriverClick -> {

            }
        }
    }
}
