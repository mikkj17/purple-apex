package com.example.purpleapex.search.presentation

import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver

data class SearchState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val showSearchOverlay: Boolean = false,
    val drivers: List<Driver> = emptyList(),
    val searchedDrivers: List<Driver> = emptyList(),
    val constructors: List<Constructor> = emptyList(),
    val searchedConstructors: List<Constructor> = emptyList(),
    val circuits: List<Circuit> = emptyList(),
    val searchedCircuits: List<Circuit> = emptyList(),
)
