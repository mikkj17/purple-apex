package com.example.purpleapex.standings.presentation.standings_list

import com.example.purpleapex.core.util.Constants
import com.example.purpleapex.standings.domain.ConstructorStandingsList
import com.example.purpleapex.standings.domain.DriverStandingsList

data class StandingsListState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedYear: Int = Constants.LATEST_F1_SEASON,
    val selectedTabIndex: Int = 0,
    val driverStandings: DriverStandingsList? = null,
    val constructorStandings: ConstructorStandingsList? = null,
)
