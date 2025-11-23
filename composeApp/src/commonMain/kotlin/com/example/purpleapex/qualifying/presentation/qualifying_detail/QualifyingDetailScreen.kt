package com.example.purpleapex.qualifying.presentation.qualifying_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.app.LocalScaffoldPadding
import com.example.purpleapex.app.LocalTopSafePadding
import com.example.purpleapex.core.presentation.components.Header
import com.example.purpleapex.qualifying.presentation.qualifying_detail.components.QualifyingInfoCard
import com.example.purpleapex.qualifying.presentation.qualifying_detail.components.ResultList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun QualifyingDetailScreenRoot(
    viewModel: QualifyingDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    QualifyingDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                QualifyingDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun QualifyingDetailScreen(
    state: QualifyingDetailState,
    onAction: (QualifyingDetailAction) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
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
                Button(onClick = { onAction(QualifyingDetailAction.OnRetryClick) }) { Text("Retry") }
            }

            state.qualifying != null -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(LocalTopSafePadding.current)
            ) {
                Header(
                    onBackClick = { onAction(QualifyingDetailAction.OnBackClick) },
                    trailingContent = {
                        Text(
                            text = state.qualifying.name,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(end = 16.dp),
                        )
                    }
                )
                Column(
                    modifier = Modifier
                        .padding(LocalScaffoldPadding.current)
                        .padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    QualifyingInfoCard(qualifying = state.qualifying)
                    Spacer(modifier = Modifier.height(16.dp))
                    ResultList(results = state.qualifying.results)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
