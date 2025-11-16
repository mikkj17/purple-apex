package com.example.purpleapex.circuit.presentation.circuit_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.circuit.domain.CircuitStats
import com.example.purpleapex.core.presentation.components.AppCard

@Composable
fun CircuitStatsCard(
    stats: CircuitStats,
    modifier: Modifier = Modifier,
) {
    AppCard(
        shape = MaterialTheme.shapes.small,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "Circuit stats",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(Modifier.height(8.dp))

            StatRow(label = "Grands Prix", value = stats.grandsPrix.toString())
            Spacer(Modifier.height(8.dp))

            StatRow(label = "First Grand Prix", value = stats.firstGrandPrixYear?.toString() ?: "—")
            Spacer(Modifier.height(8.dp))

            val driverValue = stats.topDriverName?.let { name ->
                if (stats.topDriverWins > 0) "$name (${stats.topDriverWins}x)" else name
            } ?: "—"
            StatRow(label = "Most wins (driver)", value = driverValue)
            Spacer(Modifier.height(8.dp))

            val constructorValue = stats.topConstructorName?.let { name ->
                if (stats.topConstructorWins > 0) "$name (${stats.topConstructorWins}x)" else name
            } ?: "—"
            StatRow(label = "Most wins (constructor)", value = constructorValue)
            Spacer(Modifier.height(8.dp))

            val driverPolesValue = stats.topDriverPolesName?.let { name ->
                if (stats.topDriverPoles > 0) "$name (${stats.topDriverPoles}x)" else name
            } ?: "—"
            StatRow(label = "Most poles (driver)", value = driverPolesValue)
            Spacer(Modifier.height(8.dp))

            val constructorPolesValue = stats.topConstructorPolesName?.let { name ->
                if (stats.topConstructorPoles > 0) "$name (${stats.topConstructorPoles}x)" else name
            } ?: "—"
            StatRow(label = "Most poles (constructor)", value = constructorPolesValue)
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
    }
}
