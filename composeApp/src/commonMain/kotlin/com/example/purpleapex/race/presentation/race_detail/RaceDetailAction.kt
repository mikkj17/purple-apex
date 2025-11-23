package com.example.purpleapex.race.presentation.race_detail

sealed interface RaceDetailAction {
    data object OnBackClick : RaceDetailAction
    data object OnRetryClick : RaceDetailAction
    data object OnQualifyingClick : RaceDetailAction
    data object OnLapTimesClick : RaceDetailAction
}
