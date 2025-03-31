package com.example.purpleapex.race.data.mappers

import com.example.RacesQuery
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.Location
import com.example.purpleapex.race.domain.Race

fun RacesQuery.Race.toRace() = Race(
    name = name,
    round = round,
    circuit = circuit.toCircuit(),
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
