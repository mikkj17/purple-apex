package com.example.purpleapex.race.presentation.race_list

import com.example.purpleapex.race.domain.Race
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class RaceListState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedYear: Int = Clock.System.todayIn(TimeZone.UTC).year,
    val races: List<Race> = emptyList(),
)
