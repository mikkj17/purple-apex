package com.example.purpleapex.standings.presentation.standings_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.app.LocalTopSafePadding
import com.example.purpleapex.core.presentation.components.SeasonDropdown
import com.example.purpleapex.standings.presentation.standings_list.components.ConstructorStandingListItem
import com.example.purpleapex.standings.presentation.standings_list.components.DriverStandingListItem
import com.example.purpleapex.standings.presentation.standings_list.components.StandingsList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StandingsListScreenRoot(
    viewModel: StandingsListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    StandingsListScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun StandingsListScreen(
    state: StandingsListState,
    onAction: (StandingsListAction) -> Unit,
) {
    val pagerState = rememberPagerState { 2 }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(StandingsListAction.OnTabSelected(pagerState.currentPage))
    }

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
                .padding(top = 16.dp)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "STANDINGS",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            SeasonDropdown(
                selectedYear = state.selectedYear,
                onSelectedYearChanged = {
                    onAction(StandingsListAction.SelectedYearChanged(it))
                },
            )
        }
        PrimaryTabRow(
            selectedTabIndex = state.selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth(),
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.tabIndicatorOffset(state.selectedTabIndex),
                )
            }
        ) {
            listOf("Drivers", "Constructors").forEachIndexed { index, s ->
                Tab(
                    selected = state.selectedTabIndex == index,
                    onClick = {
                        onAction(StandingsListAction.OnTabSelected(index))
                    },
                    modifier = Modifier.weight(1f),
                    selectedContentColor = MaterialTheme.colorScheme.onSurface,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ) {
                    Text(
                        text = s,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { pageIndex ->
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
                        Button(onClick = { onAction(StandingsListAction.OnRetryClick) }) { Text("Retry") }
                    }

                    state.driverStandings == null -> Text(
                        text = "No driver standings found...",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error,
                    )

                    state.constructorStandings == null -> Text(
                        text = "No constructor standings found...",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error,
                    )

                    pageIndex == 0 -> StandingsList(
                        standings = state.driverStandings.standings,
                        key = { it.driver.id },
                        content = { driverStanding ->
                            DriverStandingListItem(
                                driverStanding = driverStanding,
                            )
                        }
                    )

                    else -> StandingsList(
                        standings = state.constructorStandings.standings,
                        key = { it.constructor.id },
                        content = { constructorStanding ->
                            ConstructorStandingListItem(
                                constructorStanding = constructorStanding,
                            )
                        }
                    )
                }
            }
        }
    }
}
