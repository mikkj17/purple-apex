package com.example.purpleapex.qualifying.domain

import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver

data class Result(
    val position: Int,
    val driver: Driver,
    val constructor: Constructor,
)
