package com.example.purpleapex.constructor.data.repository

import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.constructor.domain.ConstructorClient
import com.example.purpleapex.constructor.domain.ConstructorRepository

class DefaultConstructorRepository(
    private val constructorClient: ConstructorClient,
) : ConstructorRepository {
    override suspend fun getConstructors(
        year: Int?,
        round: Int?,
        driverId: String?,
    ): List<Constructor> {
        return constructorClient.getConstructors(year, round, driverId)
    }

    override suspend fun getConstructor(constructorId: String): Constructor {
        return constructorClient
            .getConstructors()
            .first { it.id == constructorId }
    }
}
