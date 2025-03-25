package com.example.purpleapex.standings.domain

data class ConstructorStandingList(
    val season: Int,
    val round: Int,
    val standings: List<ConstructorStanding>,
)
