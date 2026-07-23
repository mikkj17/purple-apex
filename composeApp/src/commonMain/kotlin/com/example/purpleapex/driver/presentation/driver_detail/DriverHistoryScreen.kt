package com.example.purpleapex.driver.presentation.driver_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.app.LocalScaffoldPadding
import com.example.purpleapex.app.LocalTopSafePadding
import com.example.purpleapex.core.presentation.components.Header
import com.example.purpleapex.driver.presentation.driver_detail.components.QualifyingList
import com.example.purpleapex.driver.presentation.driver_detail.components.RaceList
import com.example.purpleapex.search.presentation.components.SearchBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DriverHistoryScreenRoot(
    viewModel: DriverDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onGrandPrixClick: (Int, Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DriverHistoryScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is DriverDetailAction.OnBackClick -> onBackClick()
                is DriverDetailAction.OnGrandPrixClick -> onGrandPrixClick(action.season, action.round)
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun DriverHistoryScreen(
    state: DriverDetailState,
    onAction: (DriverDetailAction) -> Unit,
) {
    val keyBoardController = LocalSoftwareKeyboardController.current
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
                Button(onClick = { onAction(DriverDetailAction.OnRetryClick) }) { Text("Retry") }
            }

            else -> {
                var selectedTabIndex by remember { mutableIntStateOf(0) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(LocalTopSafePadding.current),
                ) {
                    Header(
                        onBackClick = {
                            onAction(DriverDetailAction.OnBackClick)
                        },
                        trailingContent = {
                            SearchBar(
                                searchQuery = state.searchQuery,
                                onSearchQueryChange = {
                                    onAction(DriverDetailAction.OnSearchQueryChange(it))
                                },
                                onImeSearch = {
                                    keyBoardController?.hide()
                                },
                                placeholder = "Filter history...",
                                modifier = Modifier.padding(end = 8.dp),
                            )
                        }
                    )
                    PrimaryTabRow(
                        selectedTabIndex = selectedTabIndex,
                        contentColor = MaterialTheme.colorScheme.primary,
                        indicator = {
                            TabRowDefaults.SecondaryIndicator(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.tabIndicatorOffset(selectedTabIndex),
                            )
                        }
                    ) {
                        listOf("Races", "Qualifying").forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                selectedContentColor = MaterialTheme.colorScheme.onSurface,
                                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                text = {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(LocalScaffoldPadding.current)
                            .padding(horizontal = 8.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        when (selectedTabIndex) {
                            0 -> {
                                if (state.searchedRaces.isEmpty()) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "No races found...",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                } else {
                                    RaceList(
                                        races = state.searchedRaces,
                                        onRaceClick = { season, round ->
                                            onAction(DriverDetailAction.OnGrandPrixClick(season, round))
                                        }
                                    )
                                }
                            }

                            1 -> {
                                if (state.searchedQualifyings.isEmpty()) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "No qualifying sessions found...",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                } else {
                                    QualifyingList(
                                        qualifyings = state.searchedQualifyings,
                                        onQualifyingClick = { season, round ->
                                            onAction(DriverDetailAction.OnGrandPrixClick(season, round))
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
