package com.example.purpleapex.circuit.presentation.circuit_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.CircuitStats
import com.example.purpleapex.core.presentation.components.AppCard

@Composable
fun CircuitInfoCard(
    circuit: Circuit,
    stats: CircuitStats? = null,
    modifier: Modifier = Modifier,
) {
    AppCard(
        shape = MaterialTheme.shapes.small,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "General Information",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            LabeledValue(label = "Name", value = circuit.name)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    LabeledValue(label = "Location", value = circuit.location.locality)
                }
                Column(horizontalAlignment = Alignment.End) {
                    LabeledValue(label = "Country", value = circuit.location.country)
                }
            }

            // Stats section (when available) in the same card
            stats?.let { s ->
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(12.dp))

                val driverValue = s.topDriverName?.let { name ->
                    if (s.topDriverWins > 0) "$name (${s.topDriverWins}x)" else name
                } ?: "—"
                val constructorValue = s.topConstructorName?.let { name ->
                    if (s.topConstructorWins > 0) "$name (${s.topConstructorWins}x)" else name
                } ?: "—"
                val driverPolesValue = s.topDriverPolesName?.let { name ->
                    if (s.topDriverPoles > 0) "$name (${s.topDriverPoles}x)" else name
                } ?: "—"
                val constructorPolesValue = s.topConstructorPolesName?.let { name ->
                    if (s.topConstructorPoles > 0) "$name (${s.topConstructorPoles}x)" else name
                } ?: "—"

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column {
                        LabeledValue(label = "GPs", value = s.grandsPrix.toString())
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        LabeledValue(label = "First GP", value = s.firstGrandPrixYear?.toString() ?: "—")
                    }
                }
                Spacer(Modifier.height(8.dp))
                LabeledValue(label = "Most wins (driver)", value = driverValue)
                Spacer(Modifier.height(8.dp))
                LabeledValue(label = "Most wins (constructor)", value = constructorValue)
                Spacer(Modifier.height(8.dp))
                LabeledValue(label = "Most poles (driver)", value = driverPolesValue)
                Spacer(Modifier.height(8.dp))
                LabeledValue(label = "Most poles (constructor)", value = constructorPolesValue)
            }
        }
    }
}

@Composable
private fun LabeledValue(label: String, value: String) {
    Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
}
