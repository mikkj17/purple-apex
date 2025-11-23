package com.example.purpleapex.qualifying.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.QualifyingQuery
import com.example.QualifyingsQuery
import com.example.purpleapex.qualifying.data.mappers.toQualifying
import com.example.purpleapex.qualifying.data.mappers.toQualifyingDetail
import com.example.purpleapex.qualifying.domain.QualifyingClient
import com.example.purpleapex.qualifying.domain.QualifyingDetail

class ApolloQualifyingClient(
    private val apolloClient: ApolloClient,
) : QualifyingClient {
    override suspend fun getQualifyings(driverId: String?, constructorId: String?, circuitId: String?) = apolloClient
        .query(
            QualifyingsQuery(
                driverId = Optional.presentIfNotNull(driverId),
                constructorId = Optional.presentIfNotNull(constructorId),
                circuitId = Optional.presentIfNotNull(circuitId),
            )
        )
        .execute()
        .dataAssertNoErrors
        .qualifyings
        .map { it.toQualifying() }

    override suspend fun getQualifying(
        year: Int,
        round: Int
    ): QualifyingDetail {
        return apolloClient
            .query(QualifyingQuery(year = year, round = round))
            .execute()
            .dataAssertNoErrors
            .qualifying
            .toQualifyingDetail()
    }
}
