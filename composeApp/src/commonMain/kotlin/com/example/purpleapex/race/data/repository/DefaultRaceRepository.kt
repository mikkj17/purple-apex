package com.example.purpleapex.race.data.repository

import com.example.purpleapex.race.domain.Race
import com.example.purpleapex.race.domain.RaceClient
import com.example.purpleapex.race.domain.RaceRepository

class DefaultRaceRepository(
    private val raceClient: RaceClient
) : RaceRepository {
    override suspend fun getRaces(year: Int): List<Race> {
        return raceClient.getRaces(year)
    }
}
