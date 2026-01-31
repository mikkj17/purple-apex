package com.example.purpleapex.schedule.domain

import com.example.purpleapex.circuit.domain.Circuit

data class ScheduleDetail(
    val season: Int,
    val round: Int,
    val raceName: String,
    val date: String,
    val time: String?,
    val circuit: Circuit,
    val firstPractice: Session?,
    val secondPractice: Session?,
    val thirdPractice: Session?,
    val qualifying: Session?,
    val sprint: Session?,
    val sprintQualifying: Session?,
)
