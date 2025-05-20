package com.example.purpleapex.qualifying.domain

import com.example.purpleapex.circuit.domain.Circuit

data class Qualifying(
    val season: Int,
    val round: Int,
    val name: String,
    val date: String,
    val time: String?,
    val circuit: Circuit,
    val results: List<Result>,
)
