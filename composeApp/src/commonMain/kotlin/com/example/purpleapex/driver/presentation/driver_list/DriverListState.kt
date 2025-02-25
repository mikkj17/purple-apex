package com.example.purpleapex.driver.presentation.driver_list

import com.example.purpleapex.driver.domain.Driver

data class DriverListState(
    val drivers: List<Driver> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val searchResults: List<Driver> = emptyList(),
)
