package com.example.purpleapex.qualifying.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.QualifyingsQuery
import com.example.purpleapex.qualifying.data.mappers.toQualifying
import com.example.purpleapex.qualifying.domain.QualifyingClient

class ApolloQualifyingClient(
    private val apolloClient: ApolloClient,
) : QualifyingClient {
    override suspend fun getQualifyings(driverId: String?, constructorId: String?) = apolloClient
        .query(
            QualifyingsQuery(
                driverId = Optional.presentIfNotNull(driverId),
                constructorId = Optional.presentIfNotNull(constructorId),
            )
        )
        .execute()
        .dataAssertNoErrors
        .qualifyings
        .map { it.toQualifying() }
}
