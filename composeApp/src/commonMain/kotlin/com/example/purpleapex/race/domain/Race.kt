package com.example.purpleapex.race.domain

import com.example.purpleapex.circuit.domain.Circuit

data class Race(
    val name: String,
    val round: Int,
    val circuit: Circuit,
)
