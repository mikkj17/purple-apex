package com.example.purpleapex.circuit.domain

import com.example.purpleapex.race.domain.Race

data class CircuitStats(
    val grandsPrix: Int,
    val topDriverName: String?,
    val topDriverWins: Int,
    val topConstructorName: String?,
    val topConstructorWins: Int,
    val topDriverPolesName: String?,
    val topDriverPoles: Int,
    val topConstructorPolesName: String?,
    val topConstructorPoles: Int,
)

object CircuitStatsCalculator {
    /**
     * Compute circuit stats from the provided list of races held at this circuit.
     * - Grand Prix count
     * - Most winning driver (name and wins)
     * - Most winning constructor (name and wins)
     * - Most pole positions driver (name and poles)
     * - Most pole positions constructor (name and poles)
     */
    fun compute(races: List<Race>, qualifyings: List<com.example.purpleapex.qualifying.domain.Qualifying>): CircuitStats {
        val gp = races.size

        // Winners per race are entries with position == 1
        val winningResults = races.flatMap { race -> race.results.filter { it.position == 1 } }

        // Driver aggregation
        val driverWinGroups = winningResults.groupBy { it.driver.id }
        val topDriverEntry = driverWinGroups.maxByOrNull { it.value.size }
        val topDriverName = topDriverEntry?.value?.firstOrNull()?.driver?.fullName
        val topDriverWins = topDriverEntry?.value?.size ?: 0

        // Constructor aggregation
        val constructorWinGroups = winningResults.groupBy { it.constructor.id }
        val topConstructorEntry = constructorWinGroups.maxByOrNull { it.value.size }
        val topConstructorName = topConstructorEntry?.value?.firstOrNull()?.constructor?.name
        val topConstructorWins = topConstructorEntry?.value?.size ?: 0

        // Poles from qualifying sessions (position == 1)
        val poleResults = qualifyings.flatMap { q -> q.results.filter { it.position == 1 } }

        // Driver pole aggregation
        val driverPoleGroups = poleResults.groupBy { it.driver.id }
        val topDriverPolesEntry = driverPoleGroups.maxByOrNull { it.value.size }
        val topDriverPolesName = topDriverPolesEntry?.value?.firstOrNull()?.driver?.fullName
        val topDriverPoles = topDriverPolesEntry?.value?.size ?: 0

        // Constructor pole aggregation
        val constructorPoleGroups = poleResults.groupBy { it.constructor.id }
        val topConstructorPolesEntry = constructorPoleGroups.maxByOrNull { it.value.size }
        val topConstructorPolesName = topConstructorPolesEntry?.value?.firstOrNull()?.constructor?.name
        val topConstructorPoles = topConstructorPolesEntry?.value?.size ?: 0

        return CircuitStats(
            grandsPrix = gp,
            topDriverName = topDriverName,
            topDriverWins = topDriverWins,
            topConstructorName = topConstructorName,
            topConstructorWins = topConstructorWins,
            topDriverPolesName = topDriverPolesName,
            topDriverPoles = topDriverPoles,
            topConstructorPolesName = topConstructorPolesName,
            topConstructorPoles = topConstructorPoles,
        )
    }
}
