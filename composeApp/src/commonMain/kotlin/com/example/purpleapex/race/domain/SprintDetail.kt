package com.example.purpleapex.race.domain

import com.example.purpleapex.circuit.domain.Circuit

data class SprintDetail(
    val season: Int,
    val round: Int,
    val name: String,
    val date: String,
    val time: String?,
    val circuit: Circuit,
    val results: List<ResultDetail>,
)
