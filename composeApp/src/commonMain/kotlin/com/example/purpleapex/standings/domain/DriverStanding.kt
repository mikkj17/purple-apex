package com.example.purpleapex.standings.domain

import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver

data class DriverStanding(
    val points: Float,
    val position: Int?,
    val positionText: String,
    val wins: Int,
    val driver: Driver,
    val constructors: List<Constructor>,
)
