package com.example.purpleapex.standings.domain

data class ConstructorStanding(
    val position: Int,
    val positionText: String,
    val points: Float,
    val wins: Int,
    val constructor: Constructor,
)
