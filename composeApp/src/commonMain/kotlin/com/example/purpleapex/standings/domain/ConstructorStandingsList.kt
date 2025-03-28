package com.example.purpleapex.standings.domain

data class ConstructorStandingsList(
    val season: Int,
    val round: Int,
    val standings: List<ConstructorStanding>,
)
