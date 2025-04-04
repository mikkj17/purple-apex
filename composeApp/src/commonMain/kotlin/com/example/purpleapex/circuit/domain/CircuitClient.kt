package com.example.purpleapex.circuit.domain

interface CircuitClient {
    suspend fun getCircuits(year: Int? = null, round: Int? = null): List<Circuit>
}
