package com.example.purpleapex.standings.presentation.standings_list

import com.example.purpleapex.standings.domain.ConstructorStandingsList
import com.example.purpleapex.standings.domain.DriverStandingsList
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

data class StandingsListState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedYear: Int = Clock.System.todayIn(TimeZone.UTC).year,
    val selectedTabIndex: Int = 0,
    val driverStandings: DriverStandingsList? = null,
    val constructorStandings: ConstructorStandingsList? = null,
)
