package com.example.purpleapex.constructor.domain

import com.example.purpleapex.qualifying.domain.Qualifying
import com.example.purpleapex.race.domain.Race

data class ConstructorStats(
    val grandsPrixEntered: Int,
    val highestRaceFinish: Int?,
    val highestRaceFinishCount: Int,
    val podiums: Int,
    val highestGrid: Int?,
    val highestGridCount: Int,
    val dnfs: Int,
)

object ConstructorStatsCalculator {
    fun compute(races: List<Race>, qualifyings: List<Qualifying>): ConstructorStats {
        val raceResults = races.flatMap { race -> race.results }
        val bestFinish = raceResults.minOfOrNull { it.position }
        val bestFinishCount = if (bestFinish != null) raceResults.count { it.position == bestFinish } else 0
        val podiums = raceResults.count { it.position in 1..3 }

        val qualResults = qualifyings.flatMap { q -> q.results }
        val highestGrid = qualResults.minOfOrNull { it.position }
        val highestGridCount = if (highestGrid != null) qualResults.count { it.position == highestGrid } else 0

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

        return ConstructorStats(
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
