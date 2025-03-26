package com.example.purpleapex.standings.data.mappers

import com.example.StandingsQuery
import com.example.purpleapex.standings.domain.Constructor
import com.example.purpleapex.standings.domain.ConstructorStanding
import com.example.purpleapex.standings.domain.ConstructorStandingList
import com.example.purpleapex.standings.domain.Driver
import com.example.purpleapex.standings.domain.DriverStanding
import com.example.purpleapex.standings.domain.DriverStandingList

fun StandingsQuery.DriverStandings.toDriverStandingList() = DriverStandingList(
    season = season,
    round = round,
    standings = driverStanding.map { it.toDriverStanding() },
)

fun StandingsQuery.DriverStanding.toDriverStanding() = DriverStanding(
    points = points.toFloat(),
    position = position,
    positionText = positionText,
    wins = wins,
    driver = driver.toDriver(),
    constructors = constructors.map { it.toConstructor() },
)

fun StandingsQuery.Driver.toDriver() = Driver(
    code = code,
    dateOfBirth = dateOfBirth,
    familyName = familyName,
    givenName = givenName,
    id = id,
    nationality = nationality,
    number = number,
)

fun StandingsQuery.Constructor.toConstructor() = Constructor(
    id = id,
    name = name,
    nationality = nationality,
)

fun StandingsQuery.ConstructorStandings.toConstructorStandingList() = ConstructorStandingList(
    season = season,
    round = round,
    standings = constructorStanding.map { it.toConstructorStanding() },
)

fun StandingsQuery.ConstructorStanding.toConstructorStanding() = ConstructorStanding(
    points = points.toFloat(),
    position = position,
    positionText = positionText,
    wins = wins,
    constructor = constructor.toConstructor(),
)

fun StandingsQuery.Constructor1.toConstructor() = Constructor(
    id = id,
    name = name,
    nationality = nationality,
)
