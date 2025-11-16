package com.example.purpleapex.driver.presentation.driver_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.app.LocalScaffoldPadding
import com.example.purpleapex.app.LocalTopSafePadding
import com.example.purpleapex.core.presentation.components.AnimatedContainer
import com.example.purpleapex.core.presentation.components.AppCard
import com.example.purpleapex.core.presentation.components.Header
import com.example.purpleapex.driver.presentation.driver_detail.components.DriverInfoCard
import com.example.purpleapex.driver.presentation.driver_detail.components.DriverStatsCard
import com.example.purpleapex.driver.presentation.driver_detail.components.QualifyingList
import com.example.purpleapex.driver.presentation.driver_detail.components.RaceList
import com.example.purpleapex.search.presentation.components.SearchBar
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
            viewModel.onAction(action)
        },
    )
}

@Composable
private fun DriverDetailScreen(
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
            state.errorMessage != null -> Text(
                text = state.errorMessage,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error,
            )

            else -> Column(
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
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                )

                Column(
                    modifier = Modifier
                        .padding(LocalScaffoldPadding.current)
                        .padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DriverInfoCard(driver = state.driver!!, constructors = state.constructors)
                    state.driverStats?.let { stats ->
                        Spacer(modifier = Modifier.height(16.dp))
                        DriverStatsCard(stats = stats)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    AppCard(
                        shape = MaterialTheme.shapes.small,
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    ) {
                        AnimatedContainer(
                            header = {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Races",
                                        style = MaterialTheme.typography.headlineSmall,
                                    )
                                    Text(
                                        text = state.searchedRaces.size.toString(),
                                    )
                                }
                            },
                            content = {
                                if (state.searchedRaces.isEmpty()) Text(
                                    text = "No races found...",
                                )
                                else RaceList(
                                    races = state.searchedRaces,
                                    modifier = Modifier,
                                )
                            },
                            modifier = Modifier,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    AppCard(
                        shape = MaterialTheme.shapes.small,
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    ) {
                        AnimatedContainer(
                            header = {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Qualifying",
                                        style = MaterialTheme.typography.headlineSmall,
                                    )
                                    Text(text = state.searchedQualifyings.size.toString())
                                }
                            },
                            content = {
                                if (state.searchedQualifyings.isEmpty()) Text(
                                    text = "No qualifying sessions found...",
                                )
                                else QualifyingList(
                                    qualifyings = state.searchedQualifyings,
                                    modifier = Modifier,
                                )
                            },
                            modifier = Modifier,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
