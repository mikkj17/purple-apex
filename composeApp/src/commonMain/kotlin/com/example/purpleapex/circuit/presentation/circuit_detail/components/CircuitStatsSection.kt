package com.example.purpleapex.circuit.presentation.circuit_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.circuit.domain.CircuitStats

@Composable
fun CircuitStatsSection(
    stats: CircuitStats,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "CIRCUIT STATS",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(16.dp))
        val driverValue = stats.topDriverName?.let { name ->
            if (stats.topDriverWins > 0) "$name (${stats.topDriverWins}x)" else name
        } ?: "—"
        val constructorValue = stats.topConstructorName?.let { name ->
            if (stats.topConstructorWins > 0) "$name (${stats.topConstructorWins}x)" else name
        } ?: "—"
        val driverPolesValue = stats.topDriverPolesName?.let { name ->
            if (stats.topDriverPoles > 0) "$name (${stats.topDriverPoles}x)" else name
        } ?: "—"
        val constructorPolesValue = stats.topConstructorPolesName?.let { name ->
            if (stats.topConstructorPoles > 0) "$name (${stats.topConstructorPoles}x)" else name
        } ?: "—"

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column {
                LabeledValue(label = "Grands Prix held", value = stats.grandsPrix.toString())
            }
            Column(horizontalAlignment = Alignment.End) {
                LabeledValue(label = "First Grand Prix", value = stats.firstGrandPrixYear?.toString() ?: "—")
            }
        }
        Spacer(Modifier.height(16.dp))
        LabeledValue(label = "Most wins (driver)", value = driverValue)
        Spacer(Modifier.height(16.dp))
        LabeledValue(label = "Most wins (constructor)", value = constructorValue)
        Spacer(Modifier.height(16.dp))
        LabeledValue(label = "Most poles (driver)", value = driverPolesValue)
        Spacer(Modifier.height(16.dp))
        LabeledValue(label = "Most poles (constructor)", value = constructorPolesValue)
    }
}

@Composable
private fun LabeledValue(label: String, value: String) {
    Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    Text(
        text = label.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = FontWeight.Bold,
    )
}
