package com.example.purpleapex.qualifying.data.mappers

import com.example.QualifyingsQuery
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.Location
import com.example.purpleapex.qualifying.domain.Qualifying
import com.example.purpleapex.qualifying.domain.Result

fun QualifyingsQuery.Qualifying.toQualifying() = Qualifying(
    season = season,
    round = round,
    name = name,
    date = date,
    time = time,
    circuit = circuit.toCircuit(),
    results = results.map { it.toResult() },
)

fun QualifyingsQuery.Result.toResult() = Result(
    position = position,
)

fun QualifyingsQuery.Circuit.toCircuit() = Circuit(
    id = id,
    name = name,
    location = location.toLocation(),
)

fun QualifyingsQuery.Location.toLocation() = Location(
    country = country,
    locality = locality,
)
