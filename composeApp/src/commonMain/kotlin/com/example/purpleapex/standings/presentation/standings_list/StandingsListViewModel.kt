package com.example.purpleapex.standings.presentation.standings_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purpleapex.standings.domain.StandingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StandingsListViewModel(
    private val standingsRepository: StandingsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(StandingsListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            _state.update {
                it.copy(
                    driverStandings = standingsRepository.getDriverStandings(year = 2025),
                    isLoading = false,
                )
            }
        }
    }

    fun onAction(action: StandingsListAction) {

    }
}
