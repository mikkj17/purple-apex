package com.example.purpleapex.standings.domain

interface StandingsClient {
    suspend fun getStandings(
        year: Int,
        round: Int?
    ): Pair<DriverStandingsList?, ConstructorStandingsList?>
}
