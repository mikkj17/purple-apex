package com.example.purpleapex.driver.domain

interface DriverRepository {
    suspend fun getDrivers(year: Int? = null, round: Int? = null): List<Driver>
    suspend fun getDriver(driverId: String): DriverDetail
}
