package com.example.purpleapex.grandprix.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.app.LocalScaffoldPadding
import com.example.purpleapex.app.LocalTopSafePadding
import com.example.purpleapex.core.presentation.components.Header
import com.example.purpleapex.grandprix.presentation.components.GrandPrixInfoCard
import com.example.purpleapex.grandprix.presentation.components.Menu
import com.example.purpleapex.grandprix.presentation.components.WeekendScheduleCard
import com.example.purpleapex.race.presentation.components.ResultList
import org.koin.compose.viewmodel.koinViewModel
import com.example.purpleapex.qualifying.presentation.qualifying_detail.components.ResultList as QualifyingResultList

@Composable
fun GrandPrixDetailScreenRoot(
    viewModel: GrandPrixDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onLapTimesClick: (season: Int, round: Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    GrandPrixDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                GrandPrixDetailAction.OnBackClick -> onBackClick()
                GrandPrixDetailAction.OnLapTimesClick -> {
                    state.grandPrix?.schedule?.let {
                        onLapTimesClick(it.season, it.round)
                    }
                }

                else -> viewModel.onAction(action)
            }
        },
    )
}

@Composable
private fun GrandPrixDetailScreen(
    state: GrandPrixDetailState,
    onAction: (GrandPrixDetailAction) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
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
                Button(onClick = { onAction(GrandPrixDetailAction.OnRetryClick) }) { Text("Retry") }
            }

            state.grandPrix != null -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(LocalTopSafePadding.current)
            ) {
                Header(
                    onBackClick = { onAction(GrandPrixDetailAction.OnBackClick) },
                    trailingContent = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = state.grandPrix.schedule.raceName,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Menu(onAction = onAction)
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .padding(LocalScaffoldPadding.current)
                        .padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    GrandPrixInfoCard(schedule = state.grandPrix.schedule)
                    Spacer(modifier = Modifier.height(16.dp))

                    if (state.grandPrix.race != null || state.grandPrix.qualifying != null || state.grandPrix.sprint != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            if (state.grandPrix.race != null) {
                                FilterChip(
                                    selected = state.selectedResultType == ResultType.RACE,
                                    onClick = { onAction(GrandPrixDetailAction.OnResultTypeSelected(ResultType.RACE)) },
                                    label = { Text("Race") }
                                )
                            }
                            if (state.grandPrix.qualifying != null) {
                                FilterChip(
                                    selected = state.selectedResultType == ResultType.QUALIFYING,
                                    onClick = { onAction(GrandPrixDetailAction.OnResultTypeSelected(ResultType.QUALIFYING)) },
                                    label = { Text("Qualifying") }
                                )
                            }
                            if (state.grandPrix.sprint != null) {
                                FilterChip(
                                    selected = state.selectedResultType == ResultType.SPRINT,
                                    onClick = { onAction(GrandPrixDetailAction.OnResultTypeSelected(ResultType.SPRINT)) },
                                    label = { Text("Sprint") }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        when (state.selectedResultType) {
                            ResultType.RACE -> state.grandPrix.race?.let { ResultList(results = it.results) }
                            ResultType.QUALIFYING -> state.grandPrix.qualifying?.let { QualifyingResultList(results = it.results) }
                            ResultType.SPRINT -> state.grandPrix.sprint?.let { ResultList(results = it.results) }
                        }
                    } else {
                        WeekendScheduleCard(schedule = state.grandPrix.schedule)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
