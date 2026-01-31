package com.example.purpleapex.grandprix.presentation

import com.example.purpleapex.race.domain.GrandPrixDetail

data class GrandPrixDetailState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val grandPrix: GrandPrixDetail? = null,
    val selectedResultType: ResultType = ResultType.RACE,
)

enum class ResultType {
    RACE, QUALIFYING, SPRINT
}
