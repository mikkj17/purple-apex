package com.example.purpleapex.driver.presentation.driver_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.purpleapex.app.Route
import com.example.purpleapex.constructor.domain.ConstructorRepository
import com.example.purpleapex.core.fuzzysearch.FuzzySearch
import com.example.purpleapex.driver.domain.DriverRepository
import com.example.purpleapex.qualifying.domain.Qualifying
import com.example.purpleapex.qualifying.domain.QualifyingRepository
import com.example.purpleapex.race.domain.Race
import com.example.purpleapex.race.domain.RaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DriverDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val driverRepository: DriverRepository,
    private val constructorRepository: ConstructorRepository,
    private val raceRepository: RaceRepository,
    private val qualifyingRepository: QualifyingRepository,
) : ViewModel() {
    private val driverId = savedStateHandle.toRoute<Route.DriverDetail>().driverId
    private val _state = MutableStateFlow(DriverDetailState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            _state.update {
                it.copy(
                    driver = driverRepository.getDriver(driverId),
                    constructors = constructorRepository.getConstructors(driverId = driverId),
                    races = raceRepository.getRaces(driverId = driverId),
                    qualifyings = qualifyingRepository.getQualifyings(driverId = driverId),
                    isLoading = false,
                )
            }
            updateSearchResults()
        }
    }

    fun onAction(action: DriverDetailAction) {
        when (action) {
            is DriverDetailAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
                updateSearchResults()
            }

            is DriverDetailAction.OnBackClick -> {}
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
        getPosition: T.() -> Int,
        getSeason: T.() -> Int,
        searchKeys: T.() -> List<String>,
    ): List<T> {
        val query = _state.value.searchQuery.trim()

        if ("""[Pp]\d{1,2}""".toRegex().matches(query)) {
            return FuzzySearch.extract(
                query = query.drop(1),
                candidates = candidates,
                threshold = 1.0,
            ) {
                listOf(getPosition().toString())
            }
        }

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
            getPosition = { results.first().position },
            getSeason = { season },
            searchKeys = {
                listOf(
                    name,
                    circuit.name,
                    circuit.location.country,
                    circuit.location.locality,
                    "$season $round",
                    results.first().constructor.name,
                )
            },
        )
    }

    private fun updateQualifyings(): List<Qualifying> {
        return updateSearchResultsFor(
            candidates = _state.value.qualifyings,
            getPosition = { results.first().position },
            getSeason = { season },
            searchKeys = {
                listOf(
                    name,
                    circuit.name,
                    circuit.location.country,
                    circuit.location.locality,
                    "$season $round",
                    results.first().constructor.name,
                )
            },
        )
    }
}
