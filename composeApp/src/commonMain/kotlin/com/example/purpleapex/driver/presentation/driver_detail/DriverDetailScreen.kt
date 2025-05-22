package com.example.purpleapex.driver.presentation.driver_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            IconButton(
                onClick = {
                    onAction(DriverDetailAction.OnBackClick)
                },
            ) {
                Icon(
                    Icons.Rounded.ArrowBackIosNew,
                    contentDescription = "Go back",
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)

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

                ) {
                    Text(text = state.driver?.familyName ?: "")
                }
            }
        }
    }
}
