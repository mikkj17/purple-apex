package com.example.purpleapex.standings.presentation.standings_list

import com.example.purpleapex.standings.domain.ConstructorStandingsList
import com.example.purpleapex.standings.domain.DriverStandingsList

data class StandingsListState(
    val selectedTabIndex: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val driverStandings: DriverStandingsList? = null,
    val constructorStandings: ConstructorStandingsList? = null,
)
