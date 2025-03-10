package com.example.purpleapex.core.fuzzysearch

import kotlin.math.max

object JaroWinkler {
    private const val PREFIX_SCALING = 0.1 // Scaling factor for prefix boost (standard value)

    fun similarity(s1: String, s2: String): Double {
        if (s1 == s2) return 1.0
        if (s1.isEmpty() || s2.isEmpty()) return 0.0

        val matchDistance = (max(s1.length, s2.length) / 2) - 1
        val s1Matches = BooleanArray(s1.length)
        val s2Matches = BooleanArray(s2.length)

        var matches = 0
        var transpositions = 0

        for (i in s1.indices) {
            val start = max(0, i - matchDistance)
            val end = minOf(i + matchDistance + 1, s2.length)

            for (j in start until end) {
                if (s2Matches[j] || s1[i] != s2[j]) continue
                s1Matches[i] = true
                s2Matches[j] = true
                matches++
                break
            }
        }

        if (matches == 0) return 0.0

        var k = 0
        for (i in s1.indices) {
            if (!s1Matches[i]) continue
            while (!s2Matches[k]) k++
            if (s1[i] != s2[k]) transpositions++
            k++
        }

        val jaroSimilarity = ((matches / s1.length.toDouble()) +
                (matches / s2.length.toDouble()) +
                ((matches - transpositions / 2.0) / matches)) / 3.0

        val prefixLength = s1.commonPrefixWith(s2).length.coerceAtMost(4)
        return jaroSimilarity + (prefixLength * PREFIX_SCALING * (1 - jaroSimilarity))
    }
}
