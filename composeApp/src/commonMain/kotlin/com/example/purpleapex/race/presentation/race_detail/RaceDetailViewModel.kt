package com.example.purpleapex.race.presentation.race_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.purpleapex.app.Route
import com.example.purpleapex.race.domain.RaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RaceDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val raceRepository: RaceRepository,
) : ViewModel() {
    private val args = savedStateHandle.toRoute<Route.RaceDetail>()

    private val _state = MutableStateFlow(RaceDetailState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onAction(action: RaceDetailAction) {
        when (action) {
            RaceDetailAction.OnBackClick -> Unit
            RaceDetailAction.OnRetryClick -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                raceRepository.getRaceDetail(args.season, args.round)
            }.onSuccess { race ->
                _state.update { it.copy(race = race, isLoading = false, errorMessage = null) }
            }.onFailure { throwable ->
                _state.update { it.copy(isLoading = false, errorMessage = throwable.message ?: "Unknown error") }
            }
        }
    }
}
