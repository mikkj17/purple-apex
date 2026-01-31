package com.example.purpleapex.race.domain

interface RaceClient {
    suspend fun getRaces(year: Int?, driverId: String?, constructorId: String?, circuitId: String?): List<Race>
    suspend fun getGrandPrix(year: Int, round: Int): GrandPrixDetail
}
