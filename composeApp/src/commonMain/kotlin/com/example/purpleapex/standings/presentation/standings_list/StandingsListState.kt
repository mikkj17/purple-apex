package com.example.purpleapex.standings.presentation.standings_list

import com.example.purpleapex.standings.domain.ConstructorStandingList
import com.example.purpleapex.standings.domain.DriverStandingList

data class StandingsListState(
    val driverStandings: DriverStandingList? = null,
    val constructorStandings: ConstructorStandingList? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
