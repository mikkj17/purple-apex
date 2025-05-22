package com.example.purpleapex.driver.data.mappers

import com.example.DriverQuery
import com.example.DriversQuery
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.driver.domain.DriverDetail
import kotlinx.datetime.LocalDate

fun DriversQuery.Driver.toDriver() = Driver(
    familyName = familyName,
    givenName = givenName,
    id = id,
    nationality = nationality,
    number = number,
)

fun DriverQuery.Driver.toDriver() = DriverDetail(
    code = code,
    dateOfBirth = LocalDate.parse(dateOfBirth),
    familyName = familyName,
    givenName = givenName,
    id = id,
    nationality = nationality,
    number = number,
    url = url,
)
