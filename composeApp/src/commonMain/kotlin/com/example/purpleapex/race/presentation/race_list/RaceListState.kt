package com.example.purpleapex.race.presentation.race_list

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

data class RaceListState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedYear: Int = Clock.System.todayIn(TimeZone.UTC).year,
)
