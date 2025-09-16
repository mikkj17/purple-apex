package com.example.purpleapex.driver.presentation.driver_detail

interface DriverDetailAction {
    data class OnSearchQueryChange(val query: String) : DriverDetailAction
    data object OnBackClick : DriverDetailAction
}
