package com.example.purpleapex.standings.domain

data class DriverStandingList(
    val season: Int,
    val round: Int,
    val standings: List<DriverStanding>,
)
