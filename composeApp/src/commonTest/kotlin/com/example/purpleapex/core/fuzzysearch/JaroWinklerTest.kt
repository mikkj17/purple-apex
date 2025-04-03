package com.example.purpleapex.core.fuzzysearch

import assertk.assertThat
import assertk.assertions.isCloseTo
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import kotlin.test.Test

class JaroWinklerTest {

    @Test
    fun testIdenticalStrings() {
        val similarity = JaroWinkler.similarity("test", "test")
        assertThat(similarity).isEqualTo(1.0)
    }

    @Test
    fun testCompletelyDifferentStrings() {
        val similarity = JaroWinkler.similarity("abc", "xyz")
        assertThat(similarity).isEqualTo(0.0)
    }

    @Test
    fun testPrefixSimilarity() {
        val similarity = JaroWinkler.similarity("prefix", "preface")
        assertThat(similarity).isGreaterThan(0.8)
    }

    @Test
    fun testTranspositions() {
        val similarity = JaroWinkler.similarity("abcde", "abced")
        assertThat(similarity).isCloseTo(0.95, delta = 0.01)
    }

    @Test
    fun testEmptyStrings() {
        val similarity = JaroWinkler.similarity("", "")
        assertThat(similarity).isEqualTo(1.0)
    }

    @Test
    fun testOneEmptyString() {
        val similarity = JaroWinkler.similarity("abc", "")
        assertThat(similarity).isEqualTo(0.0)
    }
}
