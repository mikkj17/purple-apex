package com.example.purpleapex.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.CircuitRepository
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
    private val circuitRepository: CircuitRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    init {
        load()
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
            is SearchAction.OnCircuitClick -> {}
            is SearchAction.OnRetryClick -> load()
        }
    }

    private fun updateSearchResults() {
        _state.update {
            it.copy(
                searchedDrivers = updateDrivers(),
                searchedConstructors = updateConstructors(),
                searchedCircuits = updateCircuits(),
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
            listOfNotNull(
                "$givenName $familyName",
                givenName,
                familyName,
                nationality,
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

    private fun updateCircuits(): List<Circuit> {
        val circuits = _state.value.circuits
        val query = _state.value.searchQuery

        if (query.length < 3) {
            return circuits
        }

        return FuzzySearch.extract(
            query = query,
            candidates = circuits
        ) {
            listOf(
                id,
                name,
                location.country,
                location.locality,
            )
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                val drivers = driverRepository.getDrivers()
                val constructors = constructorRepository.getConstructors()
                val circuits = circuitRepository.getCircuits()
                Triple(drivers, constructors, circuits)
            }.onSuccess { (drivers, constructors, circuits) ->
                _state.update {
                    it.copy(
                        drivers = drivers,
                        constructors = constructors,
                        circuits = circuits,
                        isLoading = false,
                        errorMessage = null,
                    )
                }
                updateSearchResults()
            }.onFailure { throwable ->
                _state.update { it.copy(isLoading = false, errorMessage = throwable.message ?: "Unknown error") }
            }
        }
    }
}
