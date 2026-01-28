package com.example.purpleapex.search.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.app.LocalTopSafePadding
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.core.presentation.components.AppCard
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.search.presentation.components.CircuitSearchResultItem
import com.example.purpleapex.search.presentation.components.ConstructorSearchResultItem
import com.example.purpleapex.search.presentation.components.DriverSearchResultItem
import com.example.purpleapex.search.presentation.components.SearchBar
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreenInternal(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
) {
    val keyBoardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }

    val hasResults = state.searchedDrivers.isNotEmpty() ||
            state.searchedConstructors.isNotEmpty() ||
            state.searchedCircuits.isNotEmpty()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalTopSafePadding.current),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                SearchBar(
                    searchQuery = state.searchQuery,
                    onSearchQueryChange = {
                        onAction(SearchAction.OnSearchQueryChange(it))
                    },
                    onImeSearch = {
                        keyBoardController?.hide()
                    },
                    focusRequester = focusRequester,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (state.isLoading) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.errorMessage != null) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.errorMessage,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onAction(SearchAction.OnRetryClick) }) { Text("Retry") }
                }
            } else if (hasResults || (state.searchQuery.isNotEmpty() && !state.isLoading)) {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                ) {
                    if (state.searchedDrivers.isNotEmpty()) {
                        item {
                            ResultsContainer(title = "Drivers") {
                                state.searchedDrivers.forEach { driver ->
                                    DriverSearchResultItem(
                                        driver = driver,
                                        onClick = { onAction(SearchAction.OnDriverClick(driver)) }
                                    )
                                }
                            }
                        }
                    }

                    if (state.searchedConstructors.isNotEmpty()) {
                        item {
                            ResultsContainer(title = "Constructors") {
                                state.searchedConstructors.forEach { constructor ->
                                    ConstructorSearchResultItem(
                                        constructor = constructor,
                                        onClick = { onAction(SearchAction.OnConstructorClick(constructor)) }
                                    )
                                }
                            }
                        }
                    }

                    if (state.searchedCircuits.isNotEmpty()) {
                        item {
                            ResultsContainer(title = "Circuits") {
                                state.searchedCircuits.forEach { circuit ->
                                    CircuitSearchResultItem(
                                        circuit = circuit,
                                        onClick = { onAction(SearchAction.OnCircuitClick(circuit)) }
                                    )
                                }
                            }
                        }
                    }

                    if (state.searchQuery.isNotEmpty() && !hasResults) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No results found for \"${state.searchQuery}\"",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ResultsContainer(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            content()
    }
}

