package com.example.purpleapex.grandprix.presentation.grand_prix_list

import com.example.purpleapex.race.domain.Race
import com.example.purpleapex.schedule.domain.Schedule
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class GrandPrixListState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedYear: Int = Clock.System.todayIn(TimeZone.UTC).year,
    val races: List<Race> = emptyList(),
    val schedules: List<Schedule> = emptyList(),
)
