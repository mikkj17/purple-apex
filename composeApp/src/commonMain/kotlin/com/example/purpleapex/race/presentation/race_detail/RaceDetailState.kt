package com.example.purpleapex.race.presentation.race_detail

import com.example.purpleapex.race.domain.RaceDetail

data class RaceDetailState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val race: RaceDetail? = null,
)
