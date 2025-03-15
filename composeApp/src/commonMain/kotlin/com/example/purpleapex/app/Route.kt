package com.example.purpleapex.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Graph : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object DriverList : Route

    @Serializable
    data class DriverDetail(val driverId: String) : Route
}
