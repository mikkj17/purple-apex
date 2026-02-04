package com.example.purpleapex.constructor.presentation.constructor_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.purpleapex.app.Route
import com.example.purpleapex.constructor.domain.ConstructorRepository
import com.example.purpleapex.constructor.domain.ConstructorStatsCalculator
import com.example.purpleapex.core.fuzzysearch.FuzzySearch
import com.example.purpleapex.core.helpers.Quadruple
import com.example.purpleapex.driver.domain.DriverRepository
import com.example.purpleapex.qualifying.domain.Qualifying
import com.example.purpleapex.qualifying.domain.QualifyingRepository
import com.example.purpleapex.race.domain.Race
import com.example.purpleapex.race.domain.RaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConstructorDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val constructorRepository: ConstructorRepository,
    private val driverRepository: DriverRepository,
    private val raceRepository: RaceRepository,
    private val qualifyingRepository: QualifyingRepository,
) : ViewModel() {
    private val constructorId = savedStateHandle.toRoute<Route.ConstructorDetail>().constructorId
    private val _state = MutableStateFlow(ConstructorDetailState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onAction(action: ConstructorDetailAction) {
        when (action) {
            is ConstructorDetailAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
                updateSearchResults()
            }

            is ConstructorDetailAction.OnBackClick -> {}
            is ConstructorDetailAction.OnRetryClick -> load()
            is ConstructorDetailAction.OnGrandPrixClick -> {}
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
        getPositions: T.() -> List<String>,
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
                getPositions()
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
            getPositions = { results.map { it.position.toString() } },
            getSeason = { season },
            searchKeys = {
                arrayOf(
                    name,
                    circuit.name,
                    circuit.location.country,
                    circuit.location.locality,
                    "$season $round",
                    *results.map { it.driver.fullName }.toTypedArray()
                ).toList()
            },
        )
    }

    private fun updateQualifyings(): List<Qualifying> {
        return updateSearchResultsFor(
            candidates = _state.value.qualifyings,
            getPositions = { results.map { it.position.toString() } },
            getSeason = { season },
            searchKeys = {
                arrayOf(
                    name,
                    circuit.name,
                    circuit.location.country,
                    circuit.location.locality,
                    "$season $round",
                    *results.map { it.driver.fullName }.toTypedArray()
                ).toList()
            },
        )
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                val constructor = constructorRepository.getConstructor(constructorId = constructorId)
                val drivers = driverRepository.getDrivers(constructorId = constructorId)
                val races = raceRepository.getRaces(constructorId = constructorId)
                val qualifyings = qualifyingRepository.getQualifyings(constructorId = constructorId)
                Quadruple(constructor, drivers, races, qualifyings)
            }.onSuccess { (constructor, drivers, races, qualifyings) ->
                _state.update {
                    it.copy(
                        constructor = constructor,
                        drivers = drivers,
                        races = races,
                        qualifyings = qualifyings,
                        constructorStats = ConstructorStatsCalculator.compute(
                            races = races,
                            qualifyings = qualifyings,
                        ),
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
