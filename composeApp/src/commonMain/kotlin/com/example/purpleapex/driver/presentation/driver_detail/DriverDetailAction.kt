package com.example.purpleapex.driver.presentation.driver_detail

sealed interface DriverDetailAction {
    data class OnSearchQueryChange(val query: String) : DriverDetailAction
    data object OnBackClick : DriverDetailAction
    data object OnRetryClick : DriverDetailAction
    data class OnGrandPrixClick(val season: Int, val round: Int) : DriverDetailAction
}
