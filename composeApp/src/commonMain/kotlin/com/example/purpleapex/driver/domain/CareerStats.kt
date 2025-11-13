package com.example.purpleapex.driver.domain

import com.example.purpleapex.qualifying.domain.Qualifying
import com.example.purpleapex.race.domain.Race

data class CareerStats(
    val grandsPrixEntered: Int,
    val highestRaceFinish: Int?,
    val highestRaceFinishCount: Int,
    val podiums: Int,
    val highestGrid: Int?,
    val highestGridCount: Int,
    val polePositions: Int,
    val dnfs: Int,
)

object CareerStatsCalculator {
    fun compute(races: List<Race>, qualifyings: List<Qualifying>): CareerStats {
        val gpEntered = races.size

        // Race results are for the selected driver; conventionally the driver's result is first
        val raceResults = races.mapNotNull { it.results.firstOrNull() }

        val bestFinish = raceResults.minOfOrNull { it.position }
        val bestFinishCount = if (bestFinish != null) raceResults.count { it.position == bestFinish } else 0

        val podiums = raceResults.count { it.position in 1..3 }

        // Highest grid and poles from qualifying results
        val qualResults = qualifyings.mapNotNull { it.results.firstOrNull() }
        val highestGrid = qualResults.minOfOrNull { it.position }
        val highestGridCount = if (highestGrid != null) qualResults.count { it.position == highestGrid } else 0
        val poles = qualResults.count { it.position == 1 }

        // DNFs: based on non-numeric positionText or status keywords
        val dnfKeywords = listOf(
            "dnf", "retired", "accident", "collision", "disqualified", "engine", "gearbox", "electrical"
        )
        val dnfs = raceResults.count { res ->
            val posText = res.positionText
            val status = res.status
            val nonNumericPosText = posText != null && posText.toIntOrNull() == null
            val statusIndicatesDnf = status?.let { s -> dnfKeywords.any { kw -> s.contains(kw, ignoreCase = true) } } ?: false
            nonNumericPosText || statusIndicatesDnf
        }

        return CareerStats(
            grandsPrixEntered = gpEntered,
            highestRaceFinish = bestFinish,
            highestRaceFinishCount = bestFinishCount,
            podiums = podiums,
            highestGrid = highestGrid,
            highestGridCount = highestGridCount,
            polePositions = poles,
            dnfs = dnfs,
        )
    }

    /**
     * Compute stats for a constructor across races and qualifying.
     * The input lists are the full sessions for the constructor (may include results from multiple drivers);
     * we filter each event down to the entries where `result.constructor.id == constructorId`.
     */
    fun computeForConstructor(
        constructorId: String,
        races: List<com.example.purpleapex.race.domain.Race>,
        qualifyings: List<com.example.purpleapex.qualifying.domain.Qualifying>,
    ): CareerStats {
        // A constructor can have multiple entries (drivers) per race/session.
        // Aggregate across ALL entries for that constructor.
        val raceResults = races.flatMap { race ->
            race.results.filter { it.constructor.id == constructorId }
        }

        val gpEntered = races.count { race ->
            race.results.any { it.constructor.id == constructorId }
        }

        val bestFinish = raceResults.minOfOrNull { it.position }
        val bestFinishCount = if (bestFinish != null) raceResults.count { it.position == bestFinish } else 0

        val podiums = raceResults.count { it.position in 1..3 }

        val qualResults = qualifyings.flatMap { q ->
            q.results.filter { it.constructor.id == constructorId }
        }
        val highestGrid = qualResults.minOfOrNull { it.position }
        val highestGridCount = if (highestGrid != null) qualResults.count { it.position == highestGrid } else 0
        val poles = qualResults.count { it.position == 1 }

        val dnfKeywords = listOf(
            "dnf", "retired", "accident", "collision", "disqualified", "engine", "gearbox", "electrical"
        )
        val dnfs = raceResults.count { res ->
            val posText = res.positionText
            val status = res.status
            val nonNumericPosText = posText != null && posText.toIntOrNull() == null
            val statusIndicatesDnf = status?.let { s -> dnfKeywords.any { kw -> s.contains(kw, ignoreCase = true) } } ?: false
            nonNumericPosText || statusIndicatesDnf
        }

        return CareerStats(
            grandsPrixEntered = gpEntered,
            highestRaceFinish = bestFinish,
            highestRaceFinishCount = bestFinishCount,
            podiums = podiums,
            highestGrid = highestGrid,
            highestGridCount = highestGridCount,
            polePositions = poles,
            dnfs = dnfs,
        )
    }
}
