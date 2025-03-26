package com.example.purpleapex.standings.presentation.standings_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        onAction = { viewModel.onAction(it) }
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            selectedTabIndex = state.selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.tabIndicatorOffset(it[state.selectedTabIndex])
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
                    selectedContentColor = MaterialTheme.colorScheme.onTertiary,
                    unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = s,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
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

                    state.constructorStandings == null -> {
                        Text(
                            text = "No constructor standings found...",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }

                    pageIndex == 0 -> StandingsList(
                        standings = state.driverStandings.standings,
                        key = { it.driver.id },
                        content = { driverStanding, modifier ->
                            DriverStandingListItem(
                                driverStanding = driverStanding,
                                modifier = modifier,
                            )
                        }
                    )

                    else -> StandingsList(
                        standings = state.constructorStandings.standings,
                        key = { it.constructor.id },
                        content = { constructorStanding, modifier ->
                            ConstructorStandingListItem(
                                constructorStanding = constructorStanding,
                                modifier = modifier,
                            )
                        }
                    )
                }
            }
        }
    }
}
