package com.example.purpleapex.qualifying.domain

import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver

data class ResultDetail(
    val number: Int,
    val position: Int,
    val q1: String?,
    val q2: String?,
    val q3: String?,
    val driver: Driver,
    val constructor: Constructor,
)
