package com.example.purpleapex.standings.domain

interface StandingsRepository {
    suspend fun getStandings(
        year: Int,
        round: Int? = null
    ): Pair<DriverStandingList?, ConstructorStandingList?>
}
