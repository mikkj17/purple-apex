package com.example.purpleapex.driver.data

import com.example.DriverQuery
import com.example.DriversQuery
import com.example.purpleapex.driver.domain.Driver

fun DriverQuery.Driver.toDriver(): Driver = Driver(
    code = code,
    dateOfBirth = dateOfBirth,
    familyName = familyName,
    givenName = givenName,
    id = id,
    nationality = nationality,
    number = number,
    url = url,
)

fun DriversQuery.Driver.toDriver(): Driver = Driver(
    code = code,
    dateOfBirth = dateOfBirth,
    familyName = familyName,
    givenName = givenName,
    id = id,
    nationality = nationality,
    number = number,
    url = url,
)
