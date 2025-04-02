package com.example.purpleapex.core.fuzzysearch

import assertk.assertThat
import kotlin.test.Test
import kotlin.test.assertEquals

class JaroWinklerTest {

    @Test
    fun testIdenticalStrings() {
        val similarity = JaroWinkler.similarity("test", "test")
        assertEquals(1.0, similarity)
    }

    @Test
    fun testCompletelyDifferentStrings() {
        val similarity = JaroWinkler.similarity("abc", "xyz")
        assertEquals(0.0, similarity)
    }

    @Test
    fun testPrefixSimilarity() {
        val similarity = JaroWinkler.similarity("prefix", "preface")
        assertThat(similarity > 0.8)
    }

    @Test
    fun testTranspositions() {
        val similarity = JaroWinkler.similarity("abcde", "abced")
        assertThat(similarity in 0.85..0.95)
    }

    @Test
    fun testEmptyStrings() {
        val similarity = JaroWinkler.similarity("", "")
        assertEquals(1.0, similarity)
    }

    @Test
    fun testOneEmptyString() {
        val similarity = JaroWinkler.similarity("abc", "")
        assertEquals(0.0, similarity)
    }
}
