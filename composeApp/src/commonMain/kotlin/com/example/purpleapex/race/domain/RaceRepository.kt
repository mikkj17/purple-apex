package com.example.purpleapex.race.domain

interface RaceRepository {
    suspend fun getRaces(year: Int): List<Race>
}
