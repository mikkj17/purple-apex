package com.example.purpleapex.standings.data.repository

import com.example.purpleapex.standings.domain.DriverStandingList
import com.example.purpleapex.standings.domain.StandingsClient
import com.example.purpleapex.standings.domain.StandingsRepository

class DefaultStandingsRepository(
    private val standingsClient: StandingsClient
) : StandingsRepository {
    override suspend fun getDriverStandings(year: Int, round: Int?): DriverStandingList? {
        return standingsClient.getDriverStandings(year, round)
    }
}
