package com.example.purpleapex.race.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.QualifyingQuery
import com.example.RaceQuery
import com.example.RacesQuery
import com.example.SprintQuery
import com.example.purpleapex.qualifying.data.mappers.toQualifyingDetail
import com.example.purpleapex.race.data.mappers.toRace
import com.example.purpleapex.race.data.mappers.toRaceDetail
import com.example.purpleapex.race.data.mappers.toSprintDetail
import com.example.purpleapex.race.domain.GrandPrixDetail
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

    override suspend fun getGrandPrix(year: Int, round: Int): GrandPrixDetail {
        val raceResponse = apolloClient.query(RaceQuery(year, round)).execute()
        val qualifyingResponse = apolloClient.query(QualifyingQuery(year, round)).execute()
        val sprintResponse = try {
            apolloClient.query(SprintQuery(year, round)).execute()
        } catch (e: Exception) {
            null
        }

        val raceData = raceResponse.dataAssertNoErrors.race.toRaceDetail()
        val qualifyingData = qualifyingResponse.dataAssertNoErrors.qualifying.toQualifyingDetail()
        val sprintData = sprintResponse?.data?.sprint?.toSprintDetail()

        return GrandPrixDetail(
            race = raceData,
            qualifying = qualifyingData,
            sprint = sprintData
        )
    }
}
