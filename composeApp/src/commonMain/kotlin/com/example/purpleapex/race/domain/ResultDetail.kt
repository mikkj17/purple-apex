package com.example.purpleapex.race.domain

import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver

data class ResultDetail(
    val position: Int,
    val positionText: String,
    val points: Float,
    val status: String,
    val grid: Int,
    val laps: Int,
    val number: Int?,
    val driver: Driver,
    val constructor: Constructor,
    val fastestLap: FastestLap?,
    val time: Time?,
)
