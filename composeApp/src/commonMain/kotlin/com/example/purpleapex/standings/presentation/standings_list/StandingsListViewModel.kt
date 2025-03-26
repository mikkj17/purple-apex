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
        loadStandings()
    }

    fun onAction(action: StandingsListAction) {
        when (action) {
            is StandingsListAction.SelectedYearChanged -> {
                _state.update {
                    it.copy(selectedYear = action.year)
                }
                loadStandings()
            }

            is StandingsListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    private fun loadStandings() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val standings = standingsRepository.getStandings(year = _state.value.selectedYear)
            _state.update {
                it.copy(
                    driverStandings = standings.first,
                    constructorStandings = standings.second,
                    isLoading = false,
                )
            }
        }
    }
}
