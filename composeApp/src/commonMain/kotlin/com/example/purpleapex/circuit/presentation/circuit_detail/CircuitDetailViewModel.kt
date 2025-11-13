package com.example.purpleapex.circuit.presentation.circuit_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.purpleapex.app.Route
import com.example.purpleapex.circuit.domain.CircuitRepository
import com.example.purpleapex.circuit.domain.CircuitStatsCalculator
import com.example.purpleapex.core.fuzzysearch.FuzzySearch
import com.example.purpleapex.qualifying.domain.Qualifying
import com.example.purpleapex.qualifying.domain.QualifyingRepository
import com.example.purpleapex.race.domain.Race
import com.example.purpleapex.race.domain.RaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CircuitDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val circuitRepository: CircuitRepository,
    private val raceRepository: RaceRepository,
    private val qualifyingRepository: QualifyingRepository,
) : ViewModel() {
    private val circuitId = savedStateHandle.toRoute<Route.CircuitDetail>().circuitId
    private val _state = MutableStateFlow(CircuitDetailState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val circuit = circuitRepository.getCircuit(circuitId = circuitId)
            val races = raceRepository.getRaces(circuitId = circuitId)
            val qualifyings = qualifyingRepository.getQualifyings(circuitId = circuitId)

            _state.update {
                it.copy(
                    circuit = circuit,
                    races = races,
                    qualifyings = qualifyings,
                    circuitStats = CircuitStatsCalculator.compute(races, qualifyings),
                    isLoading = false,
                )
            }
            updateSearchResults()
        }
    }

    fun onAction(action: CircuitDetailAction) {
        when (action) {
            is CircuitDetailAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
                updateSearchResults()
            }

            is CircuitDetailAction.OnBackClick -> {}
        }
    }

    private fun updateSearchResults() {
        _state.update {
            it.copy(
                searchedRaces = updateRaces(),
                searchedQualifyings = updateQualifyings(),
            )
        }
    }

    private fun <T> updateSearchResultsFor(
        candidates: List<T>,
        getSeason: T.() -> Int,
        searchKeys: T.() -> List<String>,
    ): List<T> {
        val query = _state.value.searchQuery.trim()

        if ("""\d{4}""".toRegex().matches(query)) {
            return FuzzySearch.extract(
                query = query,
                candidates = candidates,
                threshold = 1.0,
            ) {
                listOf(getSeason().toString())
            }
        }

        if (query.length < 3) {
            return candidates
        }

        return FuzzySearch.extract(
            query = query,
            candidates = candidates,
            threshold = 0.9,
        ) {
            searchKeys()
        }
    }

    private fun updateRaces(): List<Race> {
        return updateSearchResultsFor(
            candidates = _state.value.races,
            getSeason = { season },
            searchKeys = {
                arrayOf(
                    *results.take(3).map { it.driver.fullName }.toTypedArray(),
                    *results.take(3).map { it.constructor.name }.toTypedArray(),
                ).toList()
            },
        )
    }

    private fun updateQualifyings(): List<Qualifying> {
        return updateSearchResultsFor(
            candidates = _state.value.qualifyings,
            getSeason = { season },
            searchKeys = {
                arrayOf(
                    *results.take(3).map { it.driver.fullName }.toTypedArray(),
                    *results.take(3).map { it.constructor.name }.toTypedArray(),
                ).toList()
            },
        )
    }
}
