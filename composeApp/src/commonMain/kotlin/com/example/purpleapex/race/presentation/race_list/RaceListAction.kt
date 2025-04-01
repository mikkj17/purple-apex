package com.example.purpleapex.race.presentation.race_list

sealed interface RaceListAction {
    data class SelectedYearChanged(val year: Int) : RaceListAction
}
