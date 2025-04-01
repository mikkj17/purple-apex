package com.example.purpleapex.driver.data.mappers

import com.example.DriversQuery
import com.example.purpleapex.driver.domain.Driver

fun DriversQuery.Driver.toDriver() = Driver(
    familyName = familyName,
    givenName = givenName,
    id = id,
    nationality = nationality,
    number = number,
)
