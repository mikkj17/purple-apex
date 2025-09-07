package com.example.purpleapex.driver.presentation.driver_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.driver.presentation.driver_detail.components.DriverInfoCard
import com.example.purpleapex.driver.presentation.driver_detail.components.Header
import com.example.purpleapex.driver.presentation.driver_detail.components.QualifyingList
import com.example.purpleapex.driver.presentation.driver_detail.components.RaceList
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun DriverDetailScreenRoot(
    viewModel: DriverDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DriverDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is DriverDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
        },
        onQueryChange = viewModel::onQueryChange,
        onToggleShowAllRaces = viewModel::toggleShowAllRaces,
        onToggleShowAllQualifyings = viewModel::toggleShowAllQualifyings,
        visibleRaces = viewModel::visibleRaces,
        visibleQualifyings = viewModel::visibleQualifyings,
    )
}

@Composable
private fun DriverDetailScreen(
    state: DriverDetailState,
    onAction: (DriverDetailAction) -> Unit,
    onQueryChange: (String) -> Unit,
    onToggleShowAllRaces: () -> Unit,
    onToggleShowAllQualifyings: () -> Unit,
    visibleRaces: () -> List<com.example.purpleapex.race.domain.Race>,
    visibleQualifyings: () -> List<com.example.purpleapex.qualifying.domain.Qualifying>,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            state.isLoading -> CircularProgressIndicator()
            state.errorMessage != null -> Text(
                text = state.errorMessage,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error,
            )

            else -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.tertiary),
            ) {
                Header(
                    onBackClick = {
                        onAction(DriverDetailAction.OnBackClick)
                    },
                    trailingContent = {
                        com.example.purpleapex.search.presentation.components.SearchBar(
                            searchQuery = state.query,
                            onSearchQueryChange = onQueryChange,
                            onImeSearch = {},
                            modifier = Modifier
                                .padding(end = 8.dp)
                        )
                    }
                )

                Column(modifier = Modifier.padding(8.dp).verticalScroll(rememberScrollState())) {
                    DriverInfoCard(driver = state.driver!!, constructors = state.constructors)

                    // Races with best results default
                    Column(modifier = Modifier.padding(top = 12.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Races",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onTertiary,
                            )
                            val showAllRacesText =
                                if (state.showAllRaces || state.query.isNotBlank()) "Show top ${state.resultsLimit}" else "Show all"
                            TextButton(onClick = onToggleShowAllRaces) {
                                Text(showAllRacesText)
                            }
                        }
                        RaceList(
                            races = visibleRaces(),
                            driver = state.driver,
                            modifier = Modifier
                        )
                    }

                    // Qualifying with best results default
                    Column(modifier = Modifier.padding(top = 12.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Qualifying",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onTertiary,
                            )
                            val showAllQualText =
                                if (state.showAllQualifyings || state.query.isNotBlank()) "Show top ${state.resultsLimit}" else "Show all"
                            TextButton(onClick = onToggleShowAllQualifyings) {
                                Text(showAllQualText)
                            }
                        }
                        QualifyingList(
                            qualifyings = visibleQualifyings(),
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}
