package com.example.purpleapex.driver.domain

import com.example.purpleapex.qualifying.domain.Qualifying
import com.example.purpleapex.race.domain.Race

data class DriverStats(
    val grandsPrixEntered: Int,
    val highestRaceFinish: Int?,
    val highestRaceFinishCount: Int,
    val podiums: Int,
    val highestGrid: Int?,
    val highestGridCount: Int,
    val dnfs: Int,
)

object DriverStatsCalculator {
    fun compute(races: List<Race>, qualifyings: List<Qualifying>): DriverStats {
        val raceResults = races.mapNotNull { it.results.firstOrNull() }
        val bestFinish = raceResults.minOfOrNull { it.position }
        val bestFinishCount = if (bestFinish != null) raceResults.count { it.position == bestFinish } else 0
        val podiums = raceResults.count { it.position in 1..3 }

        val qualResults = qualifyings.mapNotNull { it.results.firstOrNull() }
        val highestGrid = qualResults.minOfOrNull { it.position }
        val highestGridCount = if (highestGrid != null) qualResults.count { it.position == highestGrid } else 0

        // DNFs: based on non-numeric positionText or status keywords
        val dnfKeywords = listOf(
            "dnf", "retired", "accident", "collision", "disqualified", "engine", "gearbox", "electrical"
        )
        val dnfs = raceResults.count { res ->
            val posText = res.positionText
            val nonNumericPosText = posText != null && posText.toIntOrNull() == null
            val statusIndicatesDnf = res.status?.let { s ->
                dnfKeywords.any { kw -> s.contains(kw, ignoreCase = true) }
            } ?: false
            nonNumericPosText || statusIndicatesDnf
        }

        return DriverStats(
            grandsPrixEntered = races.size,
            highestRaceFinish = bestFinish,
            highestRaceFinishCount = bestFinishCount,
            podiums = podiums,
            highestGrid = highestGrid,
            highestGridCount = highestGridCount,
            dnfs = dnfs,
        )
    }
}
