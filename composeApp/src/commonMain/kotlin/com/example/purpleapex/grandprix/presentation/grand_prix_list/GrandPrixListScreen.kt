package com.example.purpleapex.grandprix.presentation.grand_prix_list

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
import com.example.purpleapex.app.LocalLiquidState
import com.example.purpleapex.app.LocalTopSafePadding
import com.example.purpleapex.core.presentation.components.SeasonDropdown
import com.example.purpleapex.grandprix.presentation.grand_prix_list.components.GrandPrixList
import io.github.fletchmckee.liquid.liquefiable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GrandPrixListScreenRoot(
    viewModel: GrandPrixListViewModel = koinViewModel(),
    onGrandPrixClick: (season: Int, round: Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    GrandPrixListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is GrandPrixListAction.OnRaceClick -> onGrandPrixClick(action.race.season, action.race.round)
                is GrandPrixListAction.OnScheduleClick -> onGrandPrixClick(
                    action.schedule.season,
                    action.schedule.round
                )

                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
private fun GrandPrixListScreen(
    state: GrandPrixListState,
    onAction: (GrandPrixListAction) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(LocalTopSafePadding.current)
            .liquefiable(LocalLiquidState.current),
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
                text = "GRAND PRIX",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            SeasonDropdown(
                selectedYear = state.selectedYear,
                onSelectedYearChanged = {
                    onAction(GrandPrixListAction.SelectedYearChanged(it))
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
                    Button(onClick = { onAction(GrandPrixListAction.OnRetryClick) }) { Text("Retry") }
                }

                state.races.isEmpty() && state.schedules.isEmpty() -> Text(
                    text = "No grand prix found...",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error,
                )

                else -> GrandPrixList(
                    races = state.races,
                    schedules = state.schedules,
                    onRaceClick = {
                        onAction(GrandPrixListAction.OnRaceClick(it))
                    },
                    onScheduleClick = {
                        onAction(GrandPrixListAction.OnScheduleClick(it))
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
