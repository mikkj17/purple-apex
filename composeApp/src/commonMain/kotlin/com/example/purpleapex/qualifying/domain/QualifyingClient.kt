package com.example.purpleapex.qualifying.domain

interface QualifyingClient {
    suspend fun getQualifyings(driverId: String): List<Qualifying>
}
