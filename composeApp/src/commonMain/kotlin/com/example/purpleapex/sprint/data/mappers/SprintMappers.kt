package com.example.purpleapex.sprint.data.mappers

import com.example.SprintQuery
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.Location
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.race.domain.*
import com.example.purpleapex.sprint.domain.SprintDetail

fun SprintQuery.Sprint.toSprintDetail() = SprintDetail(
    season = season,
    round = round,
    name = name,
    date = date,
    time = time,
    circuit = circuit.toSprintCircuit(),
    results = results.map { it.toSprintResultDetail() },
)

fun SprintQuery.Result.toSprintResultDetail() = ResultDetail(
    position = position,
    positionText = positionText,
    points = points.toFloat(),
    status = status,
    grid = grid,
    laps = laps,
    number = number,
    driver = driver.toSprintDriver(),
    constructor = constructor.toSprintConstructor(),
    fastestLap = fastestLap?.toSprintFastestLap(),
    time = time?.toSprintTime(),
)

fun SprintQuery.Driver.toSprintDriver() = Driver(
    id = id,
    givenName = givenName,
    familyName = familyName,
    nationality = nationality,
    number = number,
)

fun SprintQuery.Constructor.toSprintConstructor() = Constructor(
    id = id,
    name = name,
    nationality = nationality,
)

fun SprintQuery.Circuit.toSprintCircuit() = Circuit(
    id = id,
    name = name,
    location = location.toSprintLocation(),
)

fun SprintQuery.Location.toSprintLocation() = Location(
    country = country,
    locality = locality,
)

fun SprintQuery.FastestLap.toSprintFastestLap() = FastestLap(
    rank = rank,
    lap = lap,
    time = time.toSprintSimpleTime(),
    averageSpeed = averageSpeed?.toSprintAverageSpeed(),
)

fun SprintQuery.AverageSpeed.toSprintAverageSpeed() = AverageSpeed(
    speed = speed.toFloat(),
    unit = unit,
)

fun SprintQuery.Time.toSprintSimpleTime() = SimpleTime(
    time = time,
)

fun SprintQuery.Time1.toSprintTime() = Time(
    milliseconds = milliseconds,
    time = time,
)
