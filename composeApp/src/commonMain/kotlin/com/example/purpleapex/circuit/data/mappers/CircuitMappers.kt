package com.example.purpleapex.circuit.data.mappers

import com.example.CircuitsQuery
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.Location

fun CircuitsQuery.Circuit.toCircuit() = Circuit(
    id = id,
    name = name,
    location = location.toLocation(),
)

fun CircuitsQuery.Location.toLocation() = Location(
    country = country,
    locality = locality,
)
