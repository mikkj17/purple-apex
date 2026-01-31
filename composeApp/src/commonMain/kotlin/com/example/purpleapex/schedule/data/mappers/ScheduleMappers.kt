package com.example.purpleapex.schedule.data.mappers

import com.example.ScheduleQuery
import com.example.SchedulesQuery
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.Location
import com.example.purpleapex.schedule.domain.Schedule
import com.example.purpleapex.schedule.domain.ScheduleDetail
import com.example.purpleapex.schedule.domain.Session

fun SchedulesQuery.Schedule.toSchedule() = Schedule(
    season = season,
    round = round,
    raceName = raceName,
    date = date,
    time = time,
    circuit = circuit.toCircuit(),
)

fun SchedulesQuery.Circuit.toCircuit() = Circuit(
    id = id,
    name = name,
    location = location.toLocation(),
)

fun SchedulesQuery.Location.toLocation() = Location(
    country = country,
    locality = locality,
)

fun ScheduleQuery.Schedule.toScheduleDetail() = ScheduleDetail(
    season = season,
    round = round,
    raceName = raceName,
    date = date,
    time = time,
    circuit = circuit.toCircuit(),
    firstPractice = firstPractice?.toSession(),
    secondPractice = secondPractice?.toSession(),
    thirdPractice = thirdPractice?.toSession(),
    qualifying = qualifying?.toSession(),
    sprint = sprint?.toSession(),
    sprintQualifying = sprintQualifying?.toSession(),
)

fun ScheduleQuery.Circuit.toCircuit() = Circuit(
    id = id,
    name = name,
    location = location.toLocation(),
)

fun ScheduleQuery.Location.toLocation() = Location(
    country = country,
    locality = locality,
)

fun ScheduleQuery.FirstPractice.toSession() = Session(
    date = date,
    time = time,
)

fun ScheduleQuery.SecondPractice.toSession() = Session(
    date = date,
    time = time,
)

fun ScheduleQuery.ThirdPractice.toSession() = Session(
    date = date,
    time = time,
)

fun ScheduleQuery.Qualifying.toSession() = Session(
    date = date,
    time = time,
)

fun ScheduleQuery.Sprint.toSession() = Session(
    date = date,
    time = time,
)

fun ScheduleQuery.SprintQualifying.toSession() = Session(
    date = date,
    time = time,
)
