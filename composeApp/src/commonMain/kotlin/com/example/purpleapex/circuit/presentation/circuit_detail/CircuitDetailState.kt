package com.example.purpleapex.circuit.presentation.circuit_detail

import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.CircuitStats
import com.example.purpleapex.qualifying.domain.Qualifying
import com.example.purpleapex.race.domain.Race

data class CircuitDetailState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val circuit: Circuit? = null,
    val races: List<Race> = emptyList(),
    val searchedRaces: List<Race> = emptyList(),
    val qualifyings: List<Qualifying> = emptyList(),
    val searchedQualifyings: List<Qualifying> = emptyList(),
    val circuitStats: CircuitStats? = null,
)
