package com.example.purpleapex.race.domain

import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver

data class Result(
    val position: Int,
    val positionText: String? = null,
    val status: String? = null,
    val grid: Int? = null,
    val driver: Driver,
    val constructor: Constructor,
)
