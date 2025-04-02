package com.example.purpleapex.core.fuzzysearch

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
        assertTrue(result.first() == "apple")
    }

    @Test
    fun testPartialMatch() {
        val result = FuzzySearch.extract("appl", items, threshold = 0.80) { listOf(this) }
        assertTrue("apple" in result)
        assertTrue("application" in result)
    }

    @Test
    fun testNoMatch() {
        val result = FuzzySearch.extract("orange", items) { listOf(this) }
        assertTrue(result.isEmpty())
    }

    @Test
    fun testMultipleTokens() {
        val complexItems = listOf(
            "apple fruit",
            "banana peel",
            "grape vine",
        )
        val result = FuzzySearch.extract("fruit", complexItems, threshold = 0.85) { split(" ") }
        assertEquals(listOf("apple fruit"), result)
    }
}
