package com.example.purpleapex.circuit.domain

interface CircuitRepository {
    suspend fun getCircuits(year: Int? = null, round: Int? = null): List<Circuit>
    suspend fun getCircuit(circuitId: String): Circuit
}
