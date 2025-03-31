package com.example.purpleapex.constructor.data.repository

import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.constructor.domain.ConstructorClient
import com.example.purpleapex.constructor.domain.ConstructorRepository

class DefaultConstructorRepository(
    private val constructorClient: ConstructorClient
) : ConstructorRepository {
    override suspend fun getConstructors(year: Int?, round: Int?): List<Constructor> {
        return constructorClient.getConstructors(year, round)
    }
}
