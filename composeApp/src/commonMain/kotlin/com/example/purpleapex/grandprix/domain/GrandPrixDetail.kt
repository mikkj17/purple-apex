package com.example.purpleapex.grandprix.domain

import com.example.purpleapex.qualifying.domain.QualifyingDetail
import com.example.purpleapex.race.domain.RaceDetail
import com.example.purpleapex.schedule.domain.ScheduleDetail
import com.example.purpleapex.sprint.domain.SprintDetail

data class GrandPrixDetail(
    val race: RaceDetail?,
    val qualifying: QualifyingDetail?,
    val sprint: SprintDetail?,
    val schedule: ScheduleDetail,
)
