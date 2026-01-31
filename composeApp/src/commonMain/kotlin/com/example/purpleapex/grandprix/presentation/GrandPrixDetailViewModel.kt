package com.example.purpleapex.grandprix.presentation

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

class GrandPrixDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val raceRepository: RaceRepository,
) : ViewModel() {
    private val args = savedStateHandle.toRoute<Route.GrandPrixDetail>()

    private val _state = MutableStateFlow(GrandPrixDetailState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onAction(action: GrandPrixDetailAction) {
        when (action) {
            is GrandPrixDetailAction.OnRetryClick -> load()
            is GrandPrixDetailAction.OnResultTypeSelected -> {
                _state.update { it.copy(selectedResultType = action.resultType) }
            }

            else -> Unit
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { raceRepository.getGrandPrix(args.season, args.round) }
                .onSuccess { grandPrix ->
                    _state.update {
                        it.copy(
                            grandPrix = grandPrix,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { throwable ->
                    _state.update {
                        it.copy(isLoading = false, errorMessage = throwable.message ?: "Unknown error")
                    }
                }
        }
    }
}
