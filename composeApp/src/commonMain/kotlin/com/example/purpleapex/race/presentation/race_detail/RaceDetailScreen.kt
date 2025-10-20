package com.example.purpleapex.race.presentation.race_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.core.presentation.components.Header
import com.example.purpleapex.race.presentation.race_detail.components.Menu
import com.example.purpleapex.race.presentation.race_detail.components.RaceInfoCard
import com.example.purpleapex.race.presentation.race_detail.components.ResultList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RaceDetailScreenRoot(
    viewModel: RaceDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    onQualifyingClick: (season: Int, round: Int) -> Unit,
    onLapTimesClick: (season: Int, round: Int) -> Unit,
    onPitStopsClick: (season: Int, round: Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    RaceDetailScreen(
        state = state,
        onAction = { action ->
            val race = state.race!!

            when (action) {
                RaceDetailAction.OnBackClick -> onBackClick()
                RaceDetailAction.OnQualifyingClick -> onQualifyingClick(race.season, race.round)
                RaceDetailAction.OnLapTimesClick -> onLapTimesClick(race.season, race.round)
                RaceDetailAction.OnPitStopsClick -> onPitStopsClick(race.season, race.round)
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
private fun RaceDetailScreen(
    state: RaceDetailState,
    onAction: (RaceDetailAction) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        when {
            state.isLoading -> CircularProgressIndicator()
            state.errorMessage != null -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { onAction(RaceDetailAction.OnRetryClick) }) { Text("Retry") }
            }

            state.race != null -> Column(modifier = Modifier.fillMaxSize()) {
                Header(
                    onBackClick = { onAction(RaceDetailAction.OnBackClick) },
                    trailingContent = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = state.race.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                            Menu(onAction = onAction)
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    RaceInfoCard(race = state.race)
                    Spacer(modifier = Modifier.height(16.dp))
                    ResultList(results = state.race.results)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
