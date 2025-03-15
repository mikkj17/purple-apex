package com.example.purpleapex

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.driver.presentation.driver_list.DriverListScreen
import com.example.purpleapex.driver.presentation.driver_list.DriverListState
import kotlin.random.Random


private val drivers = (1..10).map {
    Driver(
        id = it.toString(),
        number = if (Random.nextBoolean()) it else null,
        givenName = "Driver $it",
        familyName = "Family $it",
        dateOfBirth = "1990-01-01",
        nationality = "Nationality $it",
        code = "Code $it",
        url = "https://www.formula1.com/en/drivers/lewis-hamilton.html",
    )
}

@Preview
@Composable
private fun DriverListScreenPreview() {
    DriverListScreen(
        state = DriverListState(
            drivers = drivers,
            searchResults = drivers,
            isLoading = false
        ),
        onAction = {},
    )
}
