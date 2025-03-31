package com.example.purpleapex.race.domain

data class FastestLap(
    val rank: Int,
    val lap: Int,
    val time: SimpleTime,
    val averageSpeed: AverageSpeed?,
)
