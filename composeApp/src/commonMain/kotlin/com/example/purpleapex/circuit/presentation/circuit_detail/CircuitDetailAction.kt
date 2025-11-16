package com.example.purpleapex.circuit.presentation.circuit_detail

sealed interface CircuitDetailAction {
    data class OnSearchQueryChange(val query: String) : CircuitDetailAction
    data object OnBackClick : CircuitDetailAction
    data object OnRetryClick : CircuitDetailAction
}
