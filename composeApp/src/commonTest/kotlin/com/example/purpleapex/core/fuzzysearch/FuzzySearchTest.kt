package com.example.purpleapex.core.fuzzysearch

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.first
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import kotlin.test.Test

class FuzzySearchTest {
    private val items = listOf(
        "apple",
        "application",
        "banana",
        "grape",
        "pineapple",
    )

    @Test
    fun testExactMatch() {
        val result = FuzzySearch.extract("apple", items) { listOf(this) }
        assertThat(result).first().isEqualTo("apple")
    }

    @Test
    fun testPartialMatch() {
        val result = FuzzySearch.extract("appl", items, threshold = 0.80) { listOf(this) }
        assertThat(result).contains("apple")
        assertThat(result).contains("application")
    }

    @Test
    fun testNoMatch() {
        val result = FuzzySearch.extract("orange", items) { listOf(this) }
        assertThat(result).isEmpty()
    }

    @Test
    fun testMultipleTokens() {
        val complexItems = listOf(
            "apple fruit",
            "banana peel",
            "grape vine",
        )
        val result = FuzzySearch.extract("fruit", complexItems, threshold = 0.85) { split(" ") }
        assertThat(result).isEqualTo(listOf("apple fruit"))
    }
}
