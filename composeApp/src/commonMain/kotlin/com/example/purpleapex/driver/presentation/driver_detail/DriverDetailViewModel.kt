package com.example.purpleapex.driver.presentation.driver_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.purpleapex.app.Route
import com.example.purpleapex.constructor.domain.ConstructorRepository
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
            _state.update { it.copy(isLoading = true) }
            val driver = driverRepository.getDriver(driverId)
            val constructors = constructorRepository.getConstructors(driverId = driverId)
            val races = raceRepository.getRaces(driverId = driverId)
            val qualifyings = qualifyingRepository.getQualifyings(driverId = driverId)
            _state.update {
                it.copy(
                    driver = driver,
                    constructors = constructors,
                    races = races,
                    qualifyings = qualifyings,
                    isLoading = false,
                )
            }
        }
    }

    fun onQueryChange(query: String) {
        _state.update { it.copy(query = query) }
    }

    fun toggleShowAllRaces() {
        _state.update { it.copy(showAllRaces = !it.showAllRaces) }
    }

    fun toggleShowAllQualifyings() {
        _state.update { it.copy(showAllQualifyings = !it.showAllQualifyings) }
    }

    fun visibleRaces(): List<Race> {
        val s = _state.value
        val query = s.query.trim().lowercase()
        val filtered = s.races.filter { race ->
            if (query.isEmpty()) return@filter true
            val circuit =
                "${race.circuit.name} ${race.circuit.location.locality} ${race.circuit.location.country}".lowercase()
            val name = race.name.lowercase()
            val seasonRound = "${race.season} ${race.round}".lowercase()
            circuit.contains(query) || name.contains(query) || seasonRound.contains(query)
        }
        val sortedByBest = filtered.sortedBy { race ->
            // pick this driver's position; if not found, put at end
            val driverId = s.driver?.id
            val pos = race.results.firstOrNull { it.driver.id == driverId }?.position
            pos ?: Int.MAX_VALUE
        }
        return if (s.showAllRaces || query.isNotEmpty()) sortedByBest else sortedByBest.take(s.resultsLimit)
    }

    fun visibleQualifyings(): List<Qualifying> {
        val s = _state.value
        val query = s.query.trim().lowercase()
        val filtered = s.qualifyings.filter { q ->
            if (query.isEmpty()) return@filter true
            val circuit = "${q.circuit.name} ${q.circuit.location.locality} ${q.circuit.location.country}".lowercase()
            val name = q.name.lowercase()
            val seasonRound = "${q.season} ${q.round}".lowercase()
            circuit.contains(query) || name.contains(query) || seasonRound.contains(query)
        }
        val sortedByBest = filtered.sortedBy { it.results.firstOrNull()?.position ?: Int.MAX_VALUE }
        return if (s.showAllQualifyings || query.isNotEmpty()) sortedByBest else sortedByBest.take(s.resultsLimit)
    }
}
