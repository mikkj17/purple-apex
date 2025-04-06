package com.example.purpleapex.circuit.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.CircuitsQuery
import com.example.purpleapex.circuit.data.mappers.toCircuit
import com.example.purpleapex.circuit.domain.CircuitClient

class ApolloCircuitClient(
    private val apolloClient: ApolloClient,
) : CircuitClient {
    override suspend fun getCircuits(year: Int?, round: Int?) = apolloClient
        .query(CircuitsQuery(Optional.presentIfNotNull(year), Optional.presentIfNotNull(round)))
        .execute()
        .dataAssertNoErrors
        .circuits
        .map { it.toCircuit() }
}
