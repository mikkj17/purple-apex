package com.example.purpleapex.grandprix.presentation.grand_prix_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purpleapex.race.domain.RaceRepository
import com.example.purpleapex.schedule.domain.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GrandPrixListViewModel(
    private val raceRepository: RaceRepository,
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {
    private val _state = MutableStateFlow(GrandPrixListState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onAction(action: GrandPrixListAction) {
        when (action) {
            is GrandPrixListAction.SelectedYearChanged -> {
                _state.update {
                    it.copy(selectedYear = action.year)
                }
                load()
            }

            is GrandPrixListAction.OnRaceClick -> {}
            is GrandPrixListAction.OnScheduleClick -> {}
            is GrandPrixListAction.OnRetryClick -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                Pair(
                    raceRepository.getRaces(year = _state.value.selectedYear),
                    scheduleRepository.getSchedules(year = _state.value.selectedYear),
                )
            }.onSuccess { (races, schedules) ->
                _state.update {
                    it.copy(
                        races = races,
                        schedules = schedules,
                        isLoading = false,
                        errorMessage = null,
                    )
                }
            }.onFailure { throwable ->
                _state.update { it.copy(isLoading = false, errorMessage = throwable.message ?: "Unknown error") }
            }
        }
    }
}
