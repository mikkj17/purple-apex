package com.example.purpleapex.standings.presentation.standings_list

sealed interface StandingsListAction {
    data class OnTabSelected(val index: Int) : StandingsListAction
}
