package com.example.purpleapex.constructor.domain

interface ConstructorClient {
    suspend fun getConstructors(
        year: Int? = null,
        round: Int? = null,
        driverId: String? = null,
    ): List<Constructor>
}
