package com.example.purpleapex.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.presentation.circuit_list.CircuitList
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.constructor.presentation.constructor_list.components.ConstructorList
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.driver.presentation.driver_list.components.DriverList
import com.example.purpleapex.search.presentation.components.SearchBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreenRoot(
    viewModel: SearchViewModel = koinViewModel(),
    onDriverClick: (Driver) -> Unit,
    onConstructorClick: (Constructor) -> Unit,
    onCircuitClick: (Circuit) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SearchScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is SearchAction.OnDriverClick -> onDriverClick(action.driver)
                is SearchAction.OnConstructorClick -> onConstructorClick(action.constructor)
                is SearchAction.OnCircuitClick -> onCircuitClick(action.circuit)
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
private fun SearchScreen(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
) {
    val keyBoardController = LocalSoftwareKeyboardController.current
    val searchResultsListState = rememberLazyGridState()
    val pagerState = rememberPagerState { 3 }

    LaunchedEffect(state.searchedDrivers) {
        searchResultsListState.animateScrollToItem(0)
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.settledPage) {
        onAction(SearchAction.OnTabSelected(pagerState.settledPage))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
    ) {
        SearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(SearchAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyBoardController?.hide()
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        )
        TabRow(
            selectedTabIndex = state.selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.tabIndicatorOffset(it[state.selectedTabIndex])
                )
            }
        ) {
            listOf("Drivers", "Constructors", "Circuits").forEachIndexed { index, s ->
                Tab(
                    selected = state.selectedTabIndex == index,
                    onClick = {
                        onAction(SearchAction.OnTabSelected(index))
                    },
                    modifier = Modifier.weight(1f),
                    selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSecondary,
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
                    state.errorMessage != null -> {
                        Text(
                            text = state.errorMessage,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }

                    pageIndex == 0 -> {
                        if (state.searchedDrivers.isEmpty())
                            Text(
                                text = "No drivers found...",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.error,
                            )
                        else
                            DriverList(
                                drivers = state.searchedDrivers,
                                onDriverClick = {
                                    onAction(SearchAction.OnDriverClick(it))
                                },
                                modifier = Modifier.fillMaxSize(),
                                scrollState = searchResultsListState,
                            )
                    }

                    pageIndex == 1 -> {
                        if (state.searchedConstructors.isEmpty())
                            Text(
                                text = "No constructors found...",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.error,
                            )
                        else
                            ConstructorList(
                                constructors = state.searchedConstructors,
                                onConstructorClick = {
                                    onAction(SearchAction.OnConstructorClick(it))
                                },
                                modifier = Modifier.fillMaxSize(),
                                scrollState = searchResultsListState,
                            )
                    }

                    else -> {
                        if (state.searchedCircuits.isEmpty())
                            Text(
                                text = "No circuits found...",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.error,
                            )
                        else
                            CircuitList(
                                circuits = state.searchedCircuits,
                                onCircuitClick = {
                                    onAction(SearchAction.OnCircuitClick(it))
                                },
                                modifier = Modifier.fillMaxSize(),
                                scrollState = searchResultsListState,
                            )
                    }
                }
            }
        }
    }
}
