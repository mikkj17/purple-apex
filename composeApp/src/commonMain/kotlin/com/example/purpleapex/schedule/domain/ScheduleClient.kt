package com.example.purpleapex.schedule.domain

interface ScheduleClient {
    suspend fun getSchedules(year: Int): List<Schedule>
}
