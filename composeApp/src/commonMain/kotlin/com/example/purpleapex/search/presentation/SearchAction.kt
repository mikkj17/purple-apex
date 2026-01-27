package com.example.purpleapex.search.presentation

import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver

sealed interface SearchAction {
    data class OnSearchQueryChange(val query: String) : SearchAction
    data class OnDriverClick(val driver: Driver) : SearchAction
    data class OnConstructorClick(val constructor: Constructor) : SearchAction
    data class OnCircuitClick(val circuit: Circuit) : SearchAction
    data object OnRetryClick : SearchAction
}
