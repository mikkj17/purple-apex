package com.example.purpleapex.standings.data.mappers

import com.example.DriverStandingsQuery
import com.example.purpleapex.standings.domain.Constructor
import com.example.purpleapex.standings.domain.Driver
import com.example.purpleapex.standings.domain.DriverStanding
import com.example.purpleapex.standings.domain.DriverStandingList

fun DriverStandingsQuery.DriverStandings.toDriverStandingList() = DriverStandingList(
    season = season,
    round = round,
    standings = standings.map { it.toDriverStanding() },
)

fun DriverStandingsQuery.Standing.toDriverStanding() = DriverStanding(
    points = points.toFloat(),
    position = position,
    positionText = positionText,
    wins = wins,
    driver = driver.toDriver(),
    constructors = constructors.map { it.toConstructor() },
)

fun DriverStandingsQuery.Driver.toDriver() = Driver(
    code = code,
    dateOfBirth = dateOfBirth,
    familyName = familyName,
    givenName = givenName,
    id = id,
    nationality = nationality,
    number = number,
)

fun DriverStandingsQuery.Constructor.toConstructor() = Constructor(
    id = id,
    name = name,
    nationality = nationality,
)
