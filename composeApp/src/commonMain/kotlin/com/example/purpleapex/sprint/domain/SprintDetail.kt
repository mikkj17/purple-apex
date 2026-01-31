package com.example.purpleapex.sprint.domain

import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.race.domain.ResultDetail

data class SprintDetail(
    val season: Int,
    val round: Int,
    val name: String,
    val date: String,
    val time: String?,
    val circuit: Circuit,
    val results: List<ResultDetail>,
)
