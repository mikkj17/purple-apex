package com.example.purpleapex.race.presentation.race_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.core.presentation.components.SeasonDropdown
import com.example.purpleapex.race.presentation.race_list.components.RaceList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RaceListScreenRoot(
    viewModel: RaceListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    RaceListScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun RaceListScreen(
    state: RaceListState,
    onAction: (RaceListAction) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(top = 16.dp)
                .padding(8.dp)
        ) {
            Text(
                text = "RACES",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            SeasonDropdown(
                selectedYear = state.selectedYear,
                onSelectedYearChanged = {
                    onAction(RaceListAction.SelectedYearChanged(it))
                },
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.errorMessage != null -> Text(
                    text = state.errorMessage,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error,
                )

                state.races.isEmpty() -> Text(
                    text = "No races found...",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error,
                )

                else -> RaceList(
                    races = state.races,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
