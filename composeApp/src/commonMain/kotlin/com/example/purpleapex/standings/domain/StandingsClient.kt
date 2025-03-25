package com.example.purpleapex.standings.domain

interface StandingsClient {
    suspend fun getDriverStandings(year: Int, round: Int?): DriverStandingList?
}
