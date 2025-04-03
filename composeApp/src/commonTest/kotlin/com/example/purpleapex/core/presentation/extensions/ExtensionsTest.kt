package com.example.purpleapex.core.presentation.extensions

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class ExtensionsKtTest {
    @Test
    fun `formatAsPoints returns integer string when value is whole number`() {
        val value = 5.0f
        val result = value.formatAsPoints()
        assertThat(result).isEqualTo("5")
    }

    @Test
    fun `formatAsPoints returns decimal string when value is decimal number`() {
        val value = 5.5f
        val result = value.formatAsPoints()
        assertThat(result).isEqualTo("5.5")
    }

    @Test
    fun `formatAsPoints returns zero string when value is zero`() {
        val value = 0.0f
        val result = value.formatAsPoints()
        assertThat(result).isEqualTo("0")
    }
}
