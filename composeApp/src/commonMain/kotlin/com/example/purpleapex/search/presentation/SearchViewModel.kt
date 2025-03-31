package com.example.purpleapex.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.constructor.domain.ConstructorRepository
import com.example.purpleapex.core.fuzzysearch.FuzzySearch
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.driver.domain.DriverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val driverRepository: DriverRepository,
    private val constructorRepository: ConstructorRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            _state.update {
                it.copy(
                    drivers = driverRepository.getDrivers(),
                    constructors = constructorRepository.getConstructors(),
                    isLoading = false,
                )
            }
            updateSearchResults()
        }
    }

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
                updateSearchResults()
            }

            is SearchAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }

            is SearchAction.OnDriverClick -> {}
            is SearchAction.OnConstructorClick -> {}
        }
    }

    private fun updateSearchResults() {
        _state.update {
            it.copy(
                searchedDrivers = updateDrivers(),
                searchedConstructors = updateConstructors(),
            )
        }
    }

    private fun updateDrivers(): List<Driver> {
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

    private fun updateConstructors(): List<Constructor> {
        val constructors = _state.value.constructors
        val query = _state.value.searchQuery

        if (query.length < 3) {
            return constructors
        }

        return FuzzySearch.extract(
            query = query,
            candidates = constructors
        ) {
            listOf(
                id,
                name,
                nationality,
            )
        }
    }
}
