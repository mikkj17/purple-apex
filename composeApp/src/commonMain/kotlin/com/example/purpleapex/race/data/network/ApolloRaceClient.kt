package com.example.purpleapex.race.data.network

import com.apollographql.apollo.ApolloClient
import com.example.RacesQuery
import com.example.purpleapex.race.data.mappers.toRace
import com.example.purpleapex.race.domain.RaceClient

class ApolloRaceClient(
    private val apolloClient: ApolloClient
) : RaceClient {
    override suspend fun getRaces(year: Int) = apolloClient
        .query(RacesQuery(year))
        .execute()
        .dataAssertNoErrors
        .races
        .map { it.toRace() }
}
