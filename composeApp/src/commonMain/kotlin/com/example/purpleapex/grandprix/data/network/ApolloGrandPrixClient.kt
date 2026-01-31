package com.example.purpleapex.grandprix.data.network

import com.apollographql.apollo.ApolloClient
import com.example.QualifyingQuery
import com.example.RaceQuery
import com.example.ScheduleQuery
import com.example.SprintQuery
import com.example.purpleapex.grandprix.domain.GrandPrixClient
import com.example.purpleapex.grandprix.domain.GrandPrixDetail
import com.example.purpleapex.qualifying.data.mappers.toQualifyingDetail
import com.example.purpleapex.race.data.mappers.toRaceDetail
import com.example.purpleapex.schedule.data.mappers.toScheduleDetail
import com.example.purpleapex.sprint.data.mappers.toSprintDetail

class ApolloGrandPrixClient(
    private val apolloClient: ApolloClient,
) : GrandPrixClient {
    override suspend fun getGrandPrix(year: Int, round: Int): GrandPrixDetail {
        val raceResponse = apolloClient.query(RaceQuery(year, round)).execute()
        val qualifyingResponse = apolloClient.query(QualifyingQuery(year, round)).execute()
        val scheduleResponse = apolloClient.query(ScheduleQuery(year, round)).execute()
        val sprintResponse = try {
            apolloClient.query(SprintQuery(year, round)).execute()
        } catch (e: Exception) {
            null
        }

        val raceData = raceResponse.data?.race?.toRaceDetail()
        val qualifyingData = qualifyingResponse.data?.qualifying?.toQualifyingDetail()
        val scheduleData = scheduleResponse.dataAssertNoErrors.schedule.toScheduleDetail()
        val sprintData = sprintResponse?.data?.sprint?.toSprintDetail()

        return GrandPrixDetail(
            race = raceData,
            qualifying = qualifyingData,
            sprint = sprintData,
            schedule = scheduleData
        )
    }
}
