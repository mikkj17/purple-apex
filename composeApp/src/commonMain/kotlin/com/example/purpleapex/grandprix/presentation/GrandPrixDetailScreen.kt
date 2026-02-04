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
            state.errorMessage != null -> ErrorMessage(
                message = state.errorMessage,
                onRetry = { onAction(GrandPrixDetailAction.OnRetryClick) }
            )

            state.grandPrix != null -> GrandPrixDetailContent(
                state = state,
                onAction = onAction
            )
        }
    }
}

@Composable
private fun ErrorMessage(
    message: String,
    onRetry: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) { Text("Retry") }
    }
}

@Composable
private fun GrandPrixDetailContent(
    state: GrandPrixDetailState,
    onAction: (GrandPrixDetailAction) -> Unit,
) {
    val grandPrix = state.grandPrix ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalTopSafePadding.current)
    ) {
        GrandPrixHeader(
            raceName = grandPrix.schedule.raceName,
            onAction = onAction
        )
        Column(
            modifier = Modifier
                .padding(LocalScaffoldPadding.current)
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            GrandPrixInfoCard(schedule = grandPrix.schedule)
            Spacer(modifier = Modifier.height(16.dp))

            if (grandPrix.race != null || grandPrix.qualifying != null || grandPrix.sprint != null) {
                ResultTypeSelector(
                    state = state,
                    onAction = onAction
                )

                Spacer(modifier = Modifier.height(8.dp))

                ResultDisplay(state = state)
            } else {
                WeekendScheduleCard(schedule = grandPrix.schedule)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun GrandPrixHeader(
    raceName: String,
    onAction: (GrandPrixDetailAction) -> Unit,
) {
    Header(
        onBackClick = { onAction(GrandPrixDetailAction.OnBackClick) },
        trailingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = raceName,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Menu(onAction = onAction)
            }
        }
    )
}

@Composable
private fun ResultTypeSelector(
    state: GrandPrixDetailState,
    onAction: (GrandPrixDetailAction) -> Unit,
) {
    val grandPrix = state.grandPrix ?: return

    if (grandPrix.qualifying == null && grandPrix.sprint == null) return

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        if (grandPrix.race != null) {
            FilterChip(
                selected = state.selectedResultType == ResultType.RACE,
                onClick = { onAction(GrandPrixDetailAction.OnResultTypeSelected(ResultType.RACE)) },
                label = { Text("Race") }
            )
        }
        if (grandPrix.qualifying != null) {
            FilterChip(
                selected = state.selectedResultType == ResultType.QUALIFYING,
                onClick = { onAction(GrandPrixDetailAction.OnResultTypeSelected(ResultType.QUALIFYING)) },
                label = { Text("Qualifying") }
            )
        }
        if (grandPrix.sprint != null) {
            FilterChip(
                selected = state.selectedResultType == ResultType.SPRINT,
                onClick = { onAction(GrandPrixDetailAction.OnResultTypeSelected(ResultType.SPRINT)) },
                label = { Text("Sprint") }
            )
        }
    }
}

@Composable
private fun ResultDisplay(state: GrandPrixDetailState) {
    val grandPrix = state.grandPrix ?: return

    when (state.selectedResultType) {
        ResultType.RACE -> grandPrix.race?.let { ResultList(results = it.results) }
        ResultType.QUALIFYING -> grandPrix.qualifying?.let { QualifyingResultList(results = it.results) }
        ResultType.SPRINT -> grandPrix.sprint?.let { ResultList(results = it.results) }
    }
}
