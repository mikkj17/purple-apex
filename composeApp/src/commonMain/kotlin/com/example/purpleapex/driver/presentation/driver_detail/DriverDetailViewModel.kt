package com.example.purpleapex.driver.presentation.driver_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.purpleapex.app.Route
import com.example.purpleapex.constructor.domain.ConstructorRepository
import com.example.purpleapex.driver.domain.DriverRepository
import com.example.purpleapex.qualifying.domain.QualifyingRepository
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
                    qualifyings = qualifyingRepository.getQualifyings(driverId),
                    isLoading = false,
                )
            }
        }
    }
}
