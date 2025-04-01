package com.example.purpleapex.race.domain

import com.example.purpleapex.circuit.domain.Circuit

data class Race(
    val season: Int,
    val round: Int,
    val name: String,
    val circuit: Circuit,
    val results: List<Result>,
)
