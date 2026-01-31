package com.example.purpleapex.race.domain

interface RaceRepository {
    suspend fun getRaces(
        year: Int? = null,
        driverId: String? = null,
        constructorId: String? = null,
        circuitId: String? = null,
    ): List<Race>

    suspend fun getGrandPrix(year: Int, round: Int): GrandPrixDetail
}
