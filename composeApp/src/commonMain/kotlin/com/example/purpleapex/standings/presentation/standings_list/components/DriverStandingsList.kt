package com.example.purpleapex.standings.presentation.standings_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.purpleapex.standings.domain.DriverStandingList

@Composable
fun DriverStandingsList(
    driverStandings: DriverStandingList,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            driverStandings.standings,
            key = { it.driver.id },
        ) { driverStanding ->
            DriverStandingListItem(
                driverStanding = driverStanding,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}
