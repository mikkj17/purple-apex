package com.example.purpleapex.driver.presentation.driver_list

import com.example.purpleapex.driver.domain.Driver

sealed interface DriverListAction {
    data class OnSearchQueryChange(val query: String) : DriverListAction
    data class OnDriverClick(val driver: Driver) : DriverListAction
}
