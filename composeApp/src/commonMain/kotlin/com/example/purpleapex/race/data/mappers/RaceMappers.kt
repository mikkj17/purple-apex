package com.example.purpleapex.race.data.mappers

import com.example.RaceQuery
import com.example.RacesQuery
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.Location
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.race.domain.*

fun RacesQuery.Race.toRace() = Race(
    season = season,
    round = round,
    name = name,
    circuit = circuit.toCircuit(),
    results = results.map { it.toResult() },
)

fun RacesQuery.Result.toResult() = Result(
    position = position,
    positionText = positionText,
    status = status,
    grid = grid,
    driver = driver.toDriver(),
    constructor = constructor.toConstructor(),
)

fun RacesQuery.Driver.toDriver() = Driver(
    id = id,
    givenName = givenName,
    familyName = familyName,
    nationality = nationality,
    number = number,
)

fun RacesQuery.Constructor.toConstructor() = Constructor(
    id = id,
    name = name,
    nationality = nationality,
)

fun RacesQuery.Circuit.toCircuit() = Circuit(
    id = id,
    name = name,
    location = location.toLocation(),
)

fun RacesQuery.Location.toLocation() = Location(
    country = country,
    locality = locality,
)

fun RaceQuery.Race.toRace() = Race(
    season = season,
    round = round,
    name = name,
    circuit = circuit.toCircuit(),
    results = results.map { it.toResult() },
)

fun RaceQuery.Race.toRaceDetail() = RaceDetail(
    season = season,
    round = round,
    name = name,
    date = date,
    time = time,
    circuit = circuit.toCircuit(),
    results = results.map { it.toResultDetail() },
)

fun RaceQuery.Result.toResult() = Result(
    position = position,
    driver = driver.toDriver(),
    constructor = constructor.toConstructor(),
)

fun RaceQuery.Result.toResultDetail() = ResultDetail(
    position = position,
    positionText = positionText,
    points = points.toFloat(),
    status = status,
    grid = grid,
    laps = laps,
    number = number,
    driver = driver.toDriver(),
    constructor = constructor.toConstructor(),
    fastestLap = fastestLap?.toFastestLap(),
    time = time?.toTime(),
)

fun RaceQuery.Driver.toDriver() = Driver(
    id = id,
    givenName = givenName,
    familyName = familyName,
    nationality = nationality,
    number = number,
)

fun RaceQuery.Constructor.toConstructor() = Constructor(
    id = id,
    name = name,
    nationality = nationality,
)

fun RaceQuery.Circuit.toCircuit() = Circuit(
    id = id,
    name = name,
    location = location.toLocation(),
)

fun RaceQuery.Location.toLocation() = Location(
    country = country,
    locality = locality,
)

fun RaceQuery.FastestLap.toFastestLap() = FastestLap(
    rank = rank,
    lap = lap,
    time = time.toSimpleTime(),
    averageSpeed = averageSpeed?.toAverageSpeed(),
)

fun RaceQuery.AverageSpeed.toAverageSpeed() = AverageSpeed(
    speed = speed.toFloat(),
    unit = unit,
)

fun RaceQuery.Time.toSimpleTime() = SimpleTime(
    time = time,
)

fun RaceQuery.Time1.toTime() = Time(
    milliseconds = milliseconds,
    time = time,
)
