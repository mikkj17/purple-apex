package com.example.purpleapex.driver.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.DriverQuery
import com.example.DriversQuery
import com.example.purpleapex.driver.data.mappers.toDriver
import com.example.purpleapex.driver.domain.DriverClient

class ApolloDriverClient(
    private val apolloClient: ApolloClient,
) : DriverClient {
    override suspend fun getDrivers(year: Int?, round: Int?, constructorId: String?) = apolloClient
        .query(
            DriversQuery(
                year = Optional.presentIfNotNull(year),
                round = Optional.presentIfNotNull(round),
                constructorId = Optional.presentIfNotNull(constructorId),
            )
        )
        .execute()
        .dataAssertNoErrors
        .drivers
        .map { it.toDriver() }

    override suspend fun getDriver(driverId: String) = apolloClient
        .query(DriverQuery(driverId))
        .execute()
        .dataAssertNoErrors
        .driver!!
        .toDriver()
}
