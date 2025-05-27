package com.example.purpleapex.driver.presentation.driver_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.driver.presentation.driver_detail.components.Header
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
        }
    )
}

@Composable
private fun DriverDetailScreen(
    state: DriverDetailState,
    onAction: (DriverDetailAction) -> Unit,
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
                modifier = Modifier.fillMaxSize()
            ) {
                Header(
                    headerText = state.driver!!.fullName,
                    onBackClick = {
                        onAction(DriverDetailAction.OnBackClick)
                    }
                )
            }
        }
    }
}
