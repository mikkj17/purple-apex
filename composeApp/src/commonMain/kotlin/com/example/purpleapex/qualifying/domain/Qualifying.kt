package com.example.purpleapex.qualifying.domain

import com.example.purpleapex.circuit.domain.Circuit

data class Qualifying(
    val season: Int,
    val round: Int,
    val url: String,
    val name: String,
    val circuit: Circuit,
    val date: String,
    val time: String?,
    val results: List<QualifyingResult>,
)
