package com.example.purpleapex.search.presentation

import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver

data class SearchState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val selectedTabIndex: Int = 0,
    val drivers: List<Driver> = emptyList(),
    val searchedDrivers: List<Driver> = emptyList(),
    val constructors: List<Constructor> = emptyList(),
    val searchedConstructors: List<Constructor> = emptyList(),
)
