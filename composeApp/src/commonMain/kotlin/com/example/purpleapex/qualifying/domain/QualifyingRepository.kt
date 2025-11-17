package com.example.purpleapex.qualifying.domain

interface QualifyingRepository {
    suspend fun getQualifyings(
        driverId: String? = null,
        constructorId: String? = null,
        circuitId: String? = null,
    ): List<Qualifying>

    suspend fun getQualifying(year: Int, round: Int): QualifyingDetail
}
