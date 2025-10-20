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
    data class RaceDetail(val season: Int, val round: Int) : Route

    @Serializable
    data class QualifyingDetail(val season: Int, val round: Int) : Route

    @Serializable
    data class LapTimes(val season: Int, val round: Int) : Route

    @Serializable
    data class PitStops(val season: Int, val round: Int) : Route

    @Serializable
    data object Search : Route

    @Serializable
    data class DriverDetail(val driverId: String) : Route

    @Serializable
    data class ConstructorDetail(val constructorId: String) : Route

    @Serializable
    data class CircuitDetail(val circuitId: String) : Route
}
