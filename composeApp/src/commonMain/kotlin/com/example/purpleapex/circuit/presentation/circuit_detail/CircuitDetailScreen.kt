package com.example.purpleapex.circuit.presentation.circuit_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.purpleapex.circuit.presentation.circuit_detail.components.CircuitInfoCard
import com.example.purpleapex.circuit.presentation.circuit_detail.components.QualifyingList
import com.example.purpleapex.circuit.presentation.circuit_detail.components.RaceList
import com.example.purpleapex.core.presentation.components.AnimatedContainer
import com.example.purpleapex.core.presentation.components.Header
import com.example.purpleapex.search.presentation.components.SearchBar
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CircuitDetailScreenRoot(
    viewModel: CircuitDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CircuitDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is CircuitDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
private fun CircuitDetailScreen(
    state: CircuitDetailState,
    onAction: (CircuitDetailAction) -> Unit,
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
                    .background(MaterialTheme.colorScheme.background),
            ) {
                Header(
                    onBackClick = {
                        onAction(CircuitDetailAction.OnBackClick)
                    },
                    trailingContent = {
                        SearchBar(
                            searchQuery = state.searchQuery,
                            onSearchQueryChange = {
                                onAction(CircuitDetailAction.OnSearchQueryChange(it))
                            },
                            onImeSearch = {
                                keyBoardController?.hide()
                            },
                            modifier = Modifier.padding(end = 8.dp),
                        )
                    }
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircuitInfoCard(circuit = state.circuit!!)
                    Spacer(modifier = Modifier.height(16.dp))
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
                                Text(text = state.searchedRaces.size.toString())
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
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = MaterialTheme.shapes.small,
                        ),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
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
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = MaterialTheme.shapes.small,
                        ),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
