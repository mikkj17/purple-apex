package com.example.purpleapex.constructor.domain

interface ConstructorRepository {
    suspend fun getConstructors(year: Int? = null, round: Int? = null): List<Constructor>
}
