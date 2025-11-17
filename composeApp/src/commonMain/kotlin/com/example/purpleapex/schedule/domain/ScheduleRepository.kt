package com.example.purpleapex.schedule.domain

interface ScheduleRepository {
    suspend fun getSchedules(year: Int): List<Schedule>
}
