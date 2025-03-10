package com.example.purpleapex.driver.presentation.driver_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purpleapex.core.fuzzysearch.FuzzySearch
import com.example.purpleapex.driver.domain.Driver
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
            updateSearchResults()
        }
    }

    fun onAction(action: DriverListAction) {
        when (action) {
            is DriverListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
                updateSearchResults()
            }

            is DriverListAction.OnDriverClick -> {

            }
        }
    }

    private fun updateSearchResults() {
        _state.update {
            it.copy(searchResults = computeSearchResults())
        }
    }

    private fun computeSearchResults(): List<Driver> {
        val drivers = _state.value.drivers
        val query = _state.value.searchQuery

        if ("""\d{1,2}""".toRegex().matches(query)) {
            return FuzzySearch.extract(
                query = query,
                candidates = drivers,
                threshold = 0.75,
            ) {
                listOf(number.toString())
            }
        }

        if (query.length < 3) {

            return drivers
        }

        return FuzzySearch.extract(
            query = query,
            candidates = drivers
        ) {
            listOf(
                "$givenName $familyName",
                givenName,
                familyName,
                nationality,
                dateOfBirth,
            )
        }
    }
}
