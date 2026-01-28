package com.example.purpleapex.search.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.purpleapex.app.LocalTopSafePadding
import com.example.purpleapex.search.presentation.components.SearchBar
import com.example.purpleapex.search.presentation.components.SearchResultItem

@Composable
fun SearchOverlay(
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
                                    SearchResultItem(
                                        title = driver.fullName,
                                        subtitle = driver.nationality,
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
                                    SearchResultItem(
                                        title = constructor.name,
                                        subtitle = constructor.nationality,
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
                                    SearchResultItem(
                                        title = circuit.name,
                                        subtitle = "${circuit.location.locality}, ${circuit.location.country}",
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

