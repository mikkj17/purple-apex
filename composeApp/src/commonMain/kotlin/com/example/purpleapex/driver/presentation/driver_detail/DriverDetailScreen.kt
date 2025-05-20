package com.example.purpleapex.driver.presentation.driver_detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun DriverDetailScreenRoot(
    viewModel: DriverDetailViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DriverDetailScreen(state)
}

@Composable
private fun DriverDetailScreen(
    state: DriverDetailState,
) {
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Text(
            state.qualifyings.firstOrNull()?.name.toString(),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
