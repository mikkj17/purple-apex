package com.example.purpleapex.qualifying.domain

interface QualifyingClient {
    suspend fun getQualifyings(driverId: String?, constructorId: String?, circuitId: String?): List<Qualifying>

    suspend fun getQualifying(year: Int, round: Int): QualifyingDetail
}
