package com.example.purpleapex.schedule.data.repository

import com.example.purpleapex.schedule.domain.Schedule
import com.example.purpleapex.schedule.domain.ScheduleClient
import com.example.purpleapex.schedule.domain.ScheduleRepository

class DefaultScheduleRepository(
    private val scheduleClient: ScheduleClient,
) : ScheduleRepository {
    override suspend fun getSchedules(year: Int): List<Schedule> {
        return scheduleClient.getSchedules(year)
    }
}
