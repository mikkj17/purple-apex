package com.example.purpleapex.standings.presentation.standings_list

import com.example.purpleapex.standings.domain.ConstructorStandingsList
import com.example.purpleapex.standings.domain.DriverStandingsList
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class StandingsListState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedYear: Int = Clock.System.todayIn(TimeZone.UTC).year,
    val selectedTabIndex: Int = 0,
    val driverStandings: DriverStandingsList? = null,
    val constructorStandings: ConstructorStandingsList? = null,
)
