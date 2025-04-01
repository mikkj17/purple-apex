package com.example.purpleapex.race.domain

interface RaceClient {
    suspend fun getRaces(year: Int): List<Race>
}
