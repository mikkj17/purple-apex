package com.example.purpleapex.race.domain

import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver

data class RaceResult(
    val number: Int,
    val position: Int,
    val positionText: String,
    val points: Float,
    val driver: Driver,
    val constructor: Constructor,
    val grid: Int,
    val laps: Int,
    val status: String,
    val time: Time?,
    val fastestLap: FastestLap?,
)
