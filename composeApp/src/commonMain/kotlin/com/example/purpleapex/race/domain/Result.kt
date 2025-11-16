package com.example.purpleapex.race.domain

import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver

data class Result(
    val position: Int,
    val positionText: String,
    val status: String,
    val grid: Int,
    val driver: Driver,
    val constructor: Constructor,
)
