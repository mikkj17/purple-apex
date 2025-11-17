package com.example.purpleapex.schedule.data.mappers

import com.example.SchedulesQuery
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.Location
import com.example.purpleapex.schedule.domain.Schedule

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
