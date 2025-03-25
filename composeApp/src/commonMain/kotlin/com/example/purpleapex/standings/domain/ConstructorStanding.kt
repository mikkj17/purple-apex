package com.example.purpleapex.standings.domain

import com.example.purpleapex.constructor.domain.Constructor

data class ConstructorStanding(
    val position: Int,
    val positionText: String,
    val points: Float,
    val wins: Int,
    val constructor: Constructor,
)
