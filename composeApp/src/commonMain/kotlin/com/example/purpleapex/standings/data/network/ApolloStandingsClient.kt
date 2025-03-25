package com.example.purpleapex.standings.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.DriverStandingsQuery
import com.example.purpleapex.standings.data.mappers.toDriverStandingList
import com.example.purpleapex.standings.domain.DriverStandingList
import com.example.purpleapex.standings.domain.StandingsClient

class ApolloStandingsClient(
    private val apolloClient: ApolloClient
) : StandingsClient {
    override suspend fun getDriverStandings(year: Int, round: Int?): DriverStandingList? {
        return apolloClient
            .query(DriverStandingsQuery(year, Optional.presentIfNotNull(round)))
            .execute()
            .dataAssertNoErrors
            .driverStandings
            ?.toDriverStandingList()
    }
}
