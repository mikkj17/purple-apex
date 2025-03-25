package com.example.purpleapex.standings.domain

data class DriverStanding(
    val points: Float,
    val position: Int?,
    val positionText: String,
    val wins: Int,
    val driver: Driver,
    val constructors: List<Constructor>,
)
