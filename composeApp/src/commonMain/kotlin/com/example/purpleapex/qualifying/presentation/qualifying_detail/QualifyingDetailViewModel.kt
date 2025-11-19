package com.example.purpleapex.qualifying.presentation.qualifying_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.purpleapex.app.Route
import com.example.purpleapex.qualifying.domain.QualifyingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QualifyingDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val qualifyingRepository: QualifyingRepository,
) : ViewModel() {
    private val args = savedStateHandle.toRoute<Route.QualifyingDetail>()

    private val _state = MutableStateFlow(QualifyingDetailState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onAction(action: QualifyingDetailAction) {
        when (action) {
            is QualifyingDetailAction.OnRetryClick -> load()
            else -> Unit
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { qualifyingRepository.getQualifying(args.season, args.round) }
                .onSuccess { qualifying ->
                    _state.update {
                        it.copy(
                            qualifying = qualifying,
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
