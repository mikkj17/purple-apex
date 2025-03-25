package com.example.purpleapex.standings.domain

interface StandingsRepository {
    suspend fun getDriverStandings(year: Int, round: Int? = null): DriverStandingList?
}
