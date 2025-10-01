package com.example.purpleapex.circuit.data.repository

import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.CircuitClient
import com.example.purpleapex.circuit.domain.CircuitRepository

class DefaultCircuitRepository(
    private val circuitClient: CircuitClient,
) : CircuitRepository {
    override suspend fun getCircuits(year: Int?, round: Int?): List<Circuit> {
        return circuitClient.getCircuits(year, round)
    }

    override suspend fun getCircuit(circuitId: String): Circuit {
        return circuitClient
            .getCircuits()
            .first { it.id == circuitId }
    }
}
