package com.example.purpleapex.race.presentation.race_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.app.LocalTopSafePadding
import com.example.purpleapex.core.presentation.components.SeasonDropdown
import com.example.purpleapex.race.domain.Race
import com.example.purpleapex.race.presentation.race_list.components.RaceList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RaceListScreenRoot(
    viewModel: RaceListViewModel = koinViewModel(),
    onRaceClick: (Race) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    RaceListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is RaceListAction.OnRaceClick -> onRaceClick(action.race)
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
private fun RaceListScreen(
    state: RaceListState,
    onAction: (RaceListAction) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(LocalTopSafePadding.current),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(top = 8.dp)
                .padding(8.dp)
        ) {
            Text(
                text = "RACES",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
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
                state.errorMessage != null -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = state.errorMessage,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onAction(RaceListAction.OnRetryClick) }) { Text("Retry") }
                }

                state.races.isEmpty() && state.schedules.isEmpty() -> Text(
                    text = "No races found...",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error,
                )

                else -> RaceList(
                    races = state.races,
                    schedules = state.schedules,
                    onRaceClick = {
                        onAction(RaceListAction.OnRaceClick(it))
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
