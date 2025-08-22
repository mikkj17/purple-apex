package com.example.purpleapex.core.presentation.components

import androidx.compose.ui.test.*
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.test.Test
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class SeasonDropdownTest {
    @OptIn(ExperimentalTestApi::class, ExperimentalTime::class)
    @Test
    fun `season dropdown expands and selects another year when clicked`() = runComposeUiTest {
        val currentYear = Clock.System.todayIn(TimeZone.UTC).year
        var selectedYear = currentYear

        setContent {
            SeasonDropdown(
                selectedYear = selectedYear,
                onSelectedYearChanged = { selectedYear = it }
            )
        }

        val testYear = currentYear - 1

        // Ensure test year is not visible
        onNodeWithText(testYear.toString()).assertDoesNotExist()

        // Click to expand dropdown
        onNodeWithText(currentYear.toString()).performClick()

        // Ensure test year is visible
        onNodeWithText(testYear.toString()).assertIsDisplayed()

        // Click to select the year
        onNodeWithText(testYear.toString()).performClick()

        // Ensure selection updated
        assertThat(selectedYear).isEqualTo(testYear)
    }
}
