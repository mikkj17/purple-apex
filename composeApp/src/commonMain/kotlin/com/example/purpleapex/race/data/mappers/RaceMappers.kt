package com.example.purpleapex.race.data.mappers

import com.example.RacesQuery
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.Location
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.race.domain.Race
import com.example.purpleapex.race.domain.Result

fun RacesQuery.Race.toRace() = Race(
    season = season,
    round = round,
    name = name,
    circuit = circuit.toCircuit(),
    results = results.map { it.toResult() },
)

fun RacesQuery.Result.toResult() = Result(
    position = position,
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
