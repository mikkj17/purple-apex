package com.example.purpleapex.standings.data.repository

import com.example.purpleapex.standings.domain.StandingsClient
import com.example.purpleapex.standings.domain.StandingsRepository

class DefaultStandingsRepository(
    private val standingsClient: StandingsClient
) : StandingsRepository {
    override suspend fun getStandings(year: Int, round: Int?) = standingsClient
        .getStandings(year, round)
}
