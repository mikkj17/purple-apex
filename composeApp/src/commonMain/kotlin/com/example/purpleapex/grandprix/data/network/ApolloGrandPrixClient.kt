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
        val sprintResponse = apolloClient.query(SprintQuery(year, round)).execute()
        val scheduleResponse = apolloClient.query(ScheduleQuery(year, round)).execute()

        return GrandPrixDetail(
            race = raceResponse.data?.race?.toRaceDetail(),
            qualifying = qualifyingResponse.data?.qualifying?.toQualifyingDetail(),
            sprint = sprintResponse.data?.sprint?.toSprintDetail(),
            schedule = scheduleResponse.dataAssertNoErrors.schedule.toScheduleDetail()
        )
    }
}
