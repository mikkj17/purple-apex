package com.example.purpleapex.driver.domain

interface DriverRepository {
    suspend fun getDriver(id: String): Driver
    suspend fun getDrivers(year: Int? = null, round: Int? = null): List<Driver>
}
