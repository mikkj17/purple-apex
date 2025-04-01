package com.example.purpleapex.schedule.domain

import com.example.purpleapex.circuit.domain.Circuit

data class Schedule(
    val season: Int,
    val round: Int,
    val raceName: String,
    val date: String,
    val time: String?,
    val circuit: Circuit,
)
