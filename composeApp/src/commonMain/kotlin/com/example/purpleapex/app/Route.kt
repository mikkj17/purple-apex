package com.example.purpleapex.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Graph : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object Standings : Route

    @Serializable
    data object Racing : Route

    @Serializable
    data object Search : Route

    @Serializable
    data class DriverDetail(val driverId: String) : Route

    @Serializable
    data class ConstructorDetail(val constructorId: String) : Route

    @Serializable
    data class CircuitDetail(val circuitId: String) : Route
}
