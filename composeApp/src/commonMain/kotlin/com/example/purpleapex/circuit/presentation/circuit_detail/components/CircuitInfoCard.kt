package com.example.purpleapex.circuit.presentation.circuit_detail.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.core.presentation.components.AnimatedContainer

@Composable
fun CircuitInfoCard(
    circuit: Circuit,
    modifier: Modifier = Modifier,
) {
    AnimatedContainer(
        header = {
            Text(
                text = "General Information",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        content = {
            Surface(
                tonalElevation = 2.dp,
                modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = MaterialTheme.shapes.small,
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
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
                }
            }
        },
        isExpandedByDefault = true,
        modifier = modifier,
    )
}

@Composable
private fun LabeledValue(label: String, value: String) {
    Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
}
