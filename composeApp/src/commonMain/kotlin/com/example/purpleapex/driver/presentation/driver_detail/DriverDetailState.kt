package com.example.purpleapex.driver.presentation.driver_detail

import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.DriverDetail
import com.example.purpleapex.qualifying.domain.Qualifying
import com.example.purpleapex.race.domain.Race

data class DriverDetailState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val driver: DriverDetail? = null,
    val constructors: List<Constructor> = emptyList(),
    val races: List<Race> = emptyList(),
    val searchedRaces: List<Race> = emptyList(),
    val qualifyings: List<Qualifying> = emptyList(),
    val searchedQualifyings: List<Qualifying> = emptyList(),
)
