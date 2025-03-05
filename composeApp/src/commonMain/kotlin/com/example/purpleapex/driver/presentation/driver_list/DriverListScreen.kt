package com.example.purpleapex.driver.presentation.driver_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.driver.presentation.driver_list.components.DriverList
import com.example.purpleapex.driver.presentation.driver_list.components.DriverSearchBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DriverListScreenRoot(
    viewModel: DriverListViewModel = koinViewModel(),
    onDriverClick: (Driver) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DriverListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is DriverListAction.OnDriverClick -> onDriverClick(action.driver)
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun DriverListScreen(
    state: DriverListState,
    onAction: (DriverListAction) -> Unit,
) {
    val keyBoardController = LocalSoftwareKeyboardController.current
    val searchResultsListState = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        searchResultsListState.animateScrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DriverSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(DriverListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyBoardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp,
            ),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Drivers",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(vertical = 16.dp),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        when {
                            state.errorMessage != null -> {
                                Text(
                                    text = state.errorMessage,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.error,
                                )
                            }

//                            state.searchResults.isEmpty() -> {
//                                Text(
//                                    text = "No drivers found...",
//                                    textAlign = TextAlign.Center,
//                                    style = MaterialTheme.typography.headlineSmall,
//                                    color = MaterialTheme.colorScheme.error,
//                                )
//                            }

                            else -> {
                                DriverList(
                                    drivers = state.drivers,
                                    onDriverClick = {
                                        onAction(DriverListAction.OnDriverClick(it))
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
    }
}
