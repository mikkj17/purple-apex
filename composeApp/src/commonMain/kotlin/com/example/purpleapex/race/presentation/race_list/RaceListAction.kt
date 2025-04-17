package com.example.purpleapex.race.presentation.race_list

import com.example.purpleapex.race.domain.Race

sealed interface RaceListAction {
    data class SelectedYearChanged(val year: Int) : RaceListAction
    data class OnRaceClick(val race: Race) : RaceListAction
}
