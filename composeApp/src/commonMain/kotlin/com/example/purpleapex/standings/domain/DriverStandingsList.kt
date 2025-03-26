package com.example.purpleapex.standings.domain

data class DriverStandingsList(
    val season: Int,
    val round: Int,
    val standings: List<DriverStanding>,
)
