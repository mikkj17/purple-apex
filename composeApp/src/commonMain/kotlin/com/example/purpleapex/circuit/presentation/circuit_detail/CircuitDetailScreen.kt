package com.example.purpleapex.circuit.presentation.circuit_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import purpleapex.composeapp.generated.resources.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.app.LocalScaffoldPadding
import com.example.purpleapex.app.LocalTopSafePadding
import com.example.purpleapex.circuit.presentation.circuit_detail.components.CircuitDetailMenu
import com.example.purpleapex.circuit.presentation.circuit_detail.components.CircuitStatsSection
import com.example.purpleapex.core.presentation.components.Header
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CircuitDetailScreenRoot(
    viewModel: CircuitDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onHistoryClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CircuitDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is CircuitDetailAction.OnBackClick -> onBackClick()
                is CircuitDetailAction.OnHistoryClick -> state.circuit?.let { onHistoryClick(it.id) }
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
                Button(onClick = { onAction(CircuitDetailAction.OnRetryClick) }) { Text("Retry") }
            }

            else -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.background,
                            )
                        )
                    )
                    .padding(LocalTopSafePadding.current),
            ) {
                Header(
                    onBackClick = {
                        onAction(CircuitDetailAction.OnBackClick)
                    },
                    backgroundColor = Color.Transparent,
                    trailingContent = {
                        CircuitDetailMenu(onAction = onAction)
                    }
                )

                Column(
                    modifier = Modifier
                        .padding(LocalScaffoldPadding.current)
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    state.circuit?.let { circuit ->
                        Text(
                            text = "${circuit.location.locality} • ${circuit.location.country}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = circuit.name.uppercase(),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Black,
                        )
                        getCircuitImage(circuit.id)?.let { image ->
                            Image(
                                painter = painterResource(image),
                                contentDescription = "${circuit.name} circuit layout",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp)
                                    .padding(vertical = 24.dp),
                            )
                        }
                        state.circuitStats?.let { stats ->
                            CircuitStatsSection(stats = stats)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                }
            }
        }
    }
}

private fun getCircuitImage(circuitId: String): DrawableResource? = when (circuitId) {
    "albert_park" -> Res.drawable.circuit_albert_park
    "americas" -> Res.drawable.cicuit_americas
    "baku" -> Res.drawable.circuit_baku
    "catalunya" -> Res.drawable.circuit_catalunya
    "hungaroring" -> Res.drawable.circuit_hungaroring
    "interlagos" -> Res.drawable.circuit_interlagos
    "losail" -> Res.drawable.circuit_losail
    "madring", "madrid" -> Res.drawable.circuit_madring
    "marina_bay" -> Res.drawable.circuit_marina_bay
    "miami" -> Res.drawable.circuit_miami
    "monaco" -> Res.drawable.circuit_monaco
    "monza" -> Res.drawable.circuit_monza
    "red_bull_ring" -> Res.drawable.circuit_red_bull_ring
    "rodriguez" -> Res.drawable.circuit_rodriguez
    "sepang" -> Res.drawable.circuit_sepang
    "shanghai" -> Res.drawable.circuit_shanghai
    "silverstone" -> Res.drawable.circuit_silverstone
    "spa" -> Res.drawable.circuit_spa
    "suzuka" -> Res.drawable.circuit_suzuka
    "vegas" -> Res.drawable.circuit_vegas
    "villeneuve" -> Res.drawable.circuit_villenueve
    "yas_marina" -> Res.drawable.circuit_yas_marina
    "zandvoort" -> Res.drawable.circuit_zandvoort
    else -> null
}
