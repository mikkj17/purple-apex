package com.example.purpleapex.grandprix.presentation

sealed interface GrandPrixDetailAction {
    data object OnBackClick : GrandPrixDetailAction
    data object OnRetryClick : GrandPrixDetailAction
    data class OnResultTypeSelected(val resultType: ResultType) : GrandPrixDetailAction
    data object OnLapTimesClick : GrandPrixDetailAction
}
