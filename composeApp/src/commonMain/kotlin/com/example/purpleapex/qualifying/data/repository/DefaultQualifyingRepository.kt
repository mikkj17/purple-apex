package com.example.purpleapex.qualifying.data.repository

import com.example.purpleapex.qualifying.domain.Qualifying
import com.example.purpleapex.qualifying.domain.QualifyingClient
import com.example.purpleapex.qualifying.domain.QualifyingDetail
import com.example.purpleapex.qualifying.domain.QualifyingRepository

class DefaultQualifyingRepository(
    private val qualifyingClient: QualifyingClient,
) : QualifyingRepository {
    override suspend fun getQualifyings(
        driverId: String?,
        constructorId: String?,
        circuitId: String?,
    ): List<Qualifying> {
        return qualifyingClient.getQualifyings(driverId, constructorId, circuitId)
    }

    override suspend fun getQualifying(
        year: Int,
        round: Int
    ): QualifyingDetail {
        return qualifyingClient.getQualifying(year, round)
    }
}
