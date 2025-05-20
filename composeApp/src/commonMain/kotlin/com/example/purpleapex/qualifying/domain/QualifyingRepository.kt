package com.example.purpleapex.qualifying.domain

interface QualifyingRepository {
    suspend fun getQualifyings(driverId: String): List<Qualifying>
}
