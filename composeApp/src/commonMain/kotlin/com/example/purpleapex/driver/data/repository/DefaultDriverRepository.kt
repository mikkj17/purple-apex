package com.example.purpleapex.driver.data.repository

import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.driver.domain.DriverClient
import com.example.purpleapex.driver.domain.DriverRepository

class DefaultDriverRepository(
    private val driverClient: DriverClient
) : DriverRepository {
    override suspend fun getDriver(id: String): Driver {
        return driverClient.getDriver(id)
    }

    override suspend fun getDrivers(year: Int?, round: Int?): List<Driver> {
        return driverClient.getDrivers(year, round)
    }
}
