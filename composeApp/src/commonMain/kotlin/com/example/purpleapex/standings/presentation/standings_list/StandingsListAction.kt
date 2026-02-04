package com.example.purpleapex.standings.presentation.standings_list

sealed interface StandingsListAction {
    data class SelectedYearChanged(val year: Int) : StandingsListAction
    data class OnTabSelected(val index: Int) : StandingsListAction
    data object OnRetryClick : StandingsListAction
    data class OnDriverClick(val driverId: String) : StandingsListAction
    data class OnConstructorClick(val constructorId: String) : StandingsListAction
}
