package com.example.purpleapex.race.domain

import com.example.purpleapex.qualifying.domain.QualifyingDetail

data class GrandPrixDetail(
    val race: RaceDetail,
    val qualifying: QualifyingDetail,
    val sprint: SprintDetail?,
)
