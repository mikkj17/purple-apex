package com.example.purpleapex.core.fuzzysearch

object FuzzySearch {
    fun <T> extract(
        query: String,
        candidates: List<T>,
        threshold: Double = 0.85,
        extractTokens: T.() -> List<String>,
    ): List<T> {
        val normalizedQuery = query.lowercase()

        return candidates
            .map { candidate ->
                candidate to candidate
                    .extractTokens()
                    .maxOf { JaroWinkler.similarity(normalizedQuery, it.lowercase()) }
            }
            .filter { it.second >= threshold }
            .sortedByDescending { it.second }
            .map { it.first }
    }
}
