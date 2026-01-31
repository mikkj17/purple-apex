package com.example.purpleapex.race.data.repository

import com.example.purpleapex.race.domain.GrandPrixDetail
import com.example.purpleapex.race.domain.Race
import com.example.purpleapex.race.domain.RaceClient
import com.example.purpleapex.race.domain.RaceRepository

class DefaultRaceRepository(
    private val raceClient: RaceClient,
) : RaceRepository {
    override suspend fun getRaces(
        year: Int?,
        driverId: String?,
        constructorId: String?,
        circuitId: String?,
    ): List<Race> {
        return raceClient.getRaces(year, driverId, constructorId, circuitId)
    }

    override suspend fun getGrandPrix(year: Int, round: Int): GrandPrixDetail {
        return raceClient.getGrandPrix(year, round)
    }
}
