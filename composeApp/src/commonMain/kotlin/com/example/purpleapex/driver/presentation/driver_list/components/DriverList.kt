package com.example.purpleapex.driver.presentation.driver_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.purpleapex.driver.domain.Driver

@Composable
fun DriverList(
    drivers: List<Driver>,
    onDriverClick: (Driver) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyGridState = rememberLazyGridState(),
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = scrollState,
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        items(
            drivers,
            key = { it.id }
        ) { driver ->
            DriverListItem(
                driver = driver,
                onClick = { onDriverClick(driver) },
                modifier = Modifier
                    .widthIn(max = 512.dp)
                    .fillMaxWidth()
            )
        }
    }
}
