package com.example.purpleapex.grandprix.presentation.grand_prix_list

import com.example.purpleapex.race.domain.Race
import com.example.purpleapex.schedule.domain.Schedule

sealed interface GrandPrixListAction {
    data class SelectedYearChanged(val year: Int) : GrandPrixListAction
    data class OnRaceClick(val race: Race) : GrandPrixListAction
    data class OnScheduleClick(val schedule: Schedule) : GrandPrixListAction
    data object OnRetryClick : GrandPrixListAction
}
