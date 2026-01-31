package com.example.purpleapex.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Graph : Route

    // Per-tab navigation subgraphs (to enable hierarchy-based selection)
    @Serializable
    data object HomeGraph : Route

    @Serializable
    data object StandingsGraph : Route

    @Serializable
    data object RacingGraph : Route

    // Tab roots
    @Serializable
    data object Home : Route

    @Serializable
    data object Standings : Route

    @Serializable
    data object Racing : Route

    @Serializable
    data class GrandPrixDetail(val season: Int, val round: Int) : Route

    @Serializable
    data class LapTimes(val season: Int, val round: Int) : Route

    @Serializable
    data class DriverDetail(val driverId: String) : Route

    @Serializable
    data class ConstructorDetail(val constructorId: String) : Route

    @Serializable
    data class CircuitDetail(val circuitId: String) : Route
}
