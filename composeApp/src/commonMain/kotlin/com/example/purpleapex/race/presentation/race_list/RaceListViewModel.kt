package com.example.purpleapex.race.presentation.race_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purpleapex.race.domain.RaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RaceListViewModel(
    private val raceRepository: RaceRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(RaceListState())
    val state = _state.asStateFlow()

    init {
        loadRaces()
    }

    fun onAction(action: RaceListAction) {
        when (action) {
            is RaceListAction.SelectedYearChanged -> {
                _state.update {
                    it.copy(selectedYear = action.year)
                }
                loadRaces()
            }

            is RaceListAction.OnRaceClick -> {}
            is RaceListAction.OnRetryClick -> loadRaces()
        }
    }

    private fun loadRaces() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { raceRepository.getRaces(year = _state.value.selectedYear) }
                .onSuccess { races ->
                    _state.update { it.copy(races = races, isLoading = false, errorMessage = null) }
                }
                .onFailure { throwable ->
                    _state.update { it.copy(isLoading = false, errorMessage = throwable.message ?: "Unknown error") }
                }
        }
    }
}
