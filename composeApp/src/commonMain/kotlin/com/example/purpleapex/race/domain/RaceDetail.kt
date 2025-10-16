package com.example.purpleapex.race.domain

import com.example.purpleapex.circuit.domain.Circuit

data class RaceDetail(
    val season: Int,
    val round: Int,
    val name: String,
    val date: String, // ISO date from API
    val time: String?, // UTC time from API, nullable if missing
    val circuit: Circuit,
    val results: List<ResultDetail>,
)
