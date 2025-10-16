package com.example.purpleapex.race.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.RaceQuery
import com.example.RacesQuery
import com.example.purpleapex.race.data.mappers.toRace
import com.example.purpleapex.race.data.mappers.toRaceDetail
import com.example.purpleapex.race.domain.RaceClient

class ApolloRaceClient(
    private val apolloClient: ApolloClient,
) : RaceClient {
    override suspend fun getRaces(year: Int?, driverId: String?, constructorId: String?, circuitId: String?) =
        apolloClient
            .query(
                RacesQuery(
                    year = Optional.presentIfNotNull(year),
                    driverId = Optional.presentIfNotNull(driverId),
                    constructorId = Optional.presentIfNotNull(constructorId),
                    circuitId = Optional.presentIfNotNull(circuitId),
                )
            )
            .execute()
            .dataAssertNoErrors
            .races
            .map { it.toRace() }

    override suspend fun getRace(year: Int, round: Int) =
        apolloClient
            .query(
                RaceQuery(
                    year = year,
                    round = round,
                )
            )
            .execute()
            .dataAssertNoErrors
            .race
            ?.toRace()
            ?: error("Race not found for year=$year, round=$round")

    override suspend fun getRaceDetail(year: Int, round: Int) =
        apolloClient
            .query(
                RaceQuery(
                    year = year,
                    round = round,
                )
            )
            .execute()
            .dataAssertNoErrors
            .race
            ?.toRaceDetail()
            ?: error("Race not found for year=$year, round=$round")
}
