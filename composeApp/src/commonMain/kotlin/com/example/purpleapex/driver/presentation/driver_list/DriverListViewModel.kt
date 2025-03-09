package com.example.purpleapex.driver.presentation.driver_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purpleapex.driver.domain.DriverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DriverListViewModel(
    private val driverRepository: DriverRepository
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
                    drivers = driverRepository.getDrivers(),
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
