package com.example.purpleapex.qualifying.data.mappers

import com.example.QualifyingQuery
import com.example.QualifyingsQuery
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.Location
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.qualifying.domain.Qualifying
import com.example.purpleapex.qualifying.domain.QualifyingDetail
import com.example.purpleapex.qualifying.domain.Result
import com.example.purpleapex.qualifying.domain.ResultDetail

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
    driver = driver.toDriver(),
    constructor = constructor.toConstructor(),
)

fun QualifyingsQuery.Driver.toDriver() = Driver(
    id = id,
    givenName = givenName,
    familyName = familyName,
    nationality = nationality,
    number = number,
)

fun QualifyingsQuery.Constructor.toConstructor() = Constructor(
    id = id,
    name = name,
    nationality = nationality,
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

fun QualifyingQuery.Qualifying.toQualifyingDetail() = QualifyingDetail(
    season = season,
    round = round,
    name = name,
    date = date,
    time = time,
    circuit = circuit.toCircuit(),
    results = results.map { it.toResultDetail() },
)

fun QualifyingQuery.Result.toResultDetail() = ResultDetail(
    number = number,
    position = position,
    q1 = q1,
    q2 = q2,
    q3 = q3,
    driver = driver.toDriver(),
    constructor = constructor.toConstructor(),
)

fun QualifyingQuery.Driver.toDriver() = Driver(
    id = id,
    givenName = givenName,
    familyName = familyName,
    nationality = nationality,
    number = number,
)

fun QualifyingQuery.Constructor.toConstructor() = Constructor(
    id = id,
    name = name,
    nationality = nationality,
)

fun QualifyingQuery.Circuit.toCircuit() = Circuit(
    id = id,
    name = name,
    location = location.toLocation(),
)

fun QualifyingQuery.Location.toLocation() = Location(
    country = country,
    locality = locality,
)
