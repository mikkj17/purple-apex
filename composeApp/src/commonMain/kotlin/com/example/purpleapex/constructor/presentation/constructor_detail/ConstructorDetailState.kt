package com.example.purpleapex.constructor.presentation.constructor_detail

import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.driver.domain.CareerStats
import com.example.purpleapex.qualifying.domain.Qualifying
import com.example.purpleapex.race.domain.Race

data class ConstructorDetailState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val constructor: Constructor? = null,
    val drivers: List<Driver> = emptyList(),
    val races: List<Race> = emptyList(),
    val searchedRaces: List<Race> = emptyList(),
    val qualifyings: List<Qualifying> = emptyList(),
    val searchedQualifyings: List<Qualifying> = emptyList(),
    val careerStats: CareerStats? = null,
)
