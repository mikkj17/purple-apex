package com.example.purpleapex.driver.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.DriversQuery
import com.example.purpleapex.driver.data.mappers.toDriver
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.driver.domain.DriverClient

class ApolloDriverClient(
    private val apolloClient: ApolloClient
) : DriverClient {
    override suspend fun getDrivers(year: Int?, round: Int?): List<Driver> {
        return apolloClient
            .query(DriversQuery(Optional.presentIfNotNull(year), Optional.presentIfNotNull(round)))
            .execute()
            .dataAssertNoErrors
            .drivers
            .map { it.toDriver() }
    }
}
