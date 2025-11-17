package com.example.purpleapex.schedule.data.network

import com.apollographql.apollo.ApolloClient
import com.example.SchedulesQuery
import com.example.purpleapex.schedule.data.mappers.toSchedule
import com.example.purpleapex.schedule.domain.Schedule
import com.example.purpleapex.schedule.domain.ScheduleClient

class ApolloScheduleClient(
    private val apolloClient: ApolloClient,
) : ScheduleClient {
    override suspend fun getSchedules(year: Int): List<Schedule> {
        return apolloClient
            .query(SchedulesQuery(year = year))
            .execute()
            .dataAssertNoErrors
            .schedules
            .map { it.toSchedule() }
    }
}
