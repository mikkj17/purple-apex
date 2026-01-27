package com.example.purpleapex.standings.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.StandingsQuery
import com.example.purpleapex.standings.data.mappers.toConstructorStandingList
import com.example.purpleapex.standings.data.mappers.toDriverStandingList
import com.example.purpleapex.standings.domain.StandingsClient

class ApolloStandingsClient(
    private val apolloClient: ApolloClient
) : StandingsClient {
    override suspend fun getStandings(year: Int, round: Int?) = apolloClient
        .query(StandingsQuery(year, Optional.presentIfNotNull(round)))
        .execute()
        .dataAssertNoErrors
        .let {
            Pair(
                it.driverStandings.toDriverStandingList(),
                it.constructorStandings.toConstructorStandingList()
            )
        }
}
