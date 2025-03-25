package com.example.purpleapex.standings.presentation.standings_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.purpleapex.standings.presentation.standings_list.components.DriverStandingsList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StandingsListScreenRoot(
    viewModel: StandingsListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    StandingsListScreen(
        state = state,
        onAction = { viewModel.onAction(it) }
    )
}

@Composable
private fun StandingsListScreen(
    state: StandingsListState,
    onAction: (StandingsListAction) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> CircularProgressIndicator()
            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            state.driverStandings == null -> {
                Text(
                    text = "No driver standings found...",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            else -> {
                DriverStandingsList(
                    state.driverStandings,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
