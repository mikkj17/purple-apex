package com.example.purpleapex.constructor.presentation.constructor_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.constructor.domain.ConstructorStats
import com.example.purpleapex.core.presentation.components.AppCard

@Composable
fun ConstructorInfoCard(
    constructor: Constructor,
    stats: ConstructorStats,
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
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    LabeledValue(label = "Name", value = constructor.name)
                }
                Column(horizontalAlignment = Alignment.End) {
                    LabeledValue(label = "Nationality", value = constructor.nationality)
                }
            }

            // Stats section (when available) in the same card
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Stats",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(Modifier.height(8.dp))

            StatRow(label = "Grand Prix entered", value = stats.grandsPrixEntered.toString())
            Spacer(Modifier.height(8.dp))

            val highestFinish = stats.highestRaceFinish
            val hasWon = highestFinish == 1
            val highestFinishSuffix = if (stats.highestRaceFinishCount > 1)
                " (${stats.highestRaceFinishCount}x)"
            else ""
            StatRow(
                label = if (hasWon) "Wins" else "Highest race finish",
                value = if (hasWon) stats.highestRaceFinishCount.toString()
                else highestFinish?.let { "P$it$highestFinishSuffix" } ?: "—",
            )
            Spacer(Modifier.height(8.dp))

            StatRow(label = "Podiums", value = stats.podiums.toString())
            Spacer(Modifier.height(8.dp))

            val highestGrid = stats.highestGrid
            val hasPole = highestGrid == 1
            val highestGridSuffix = if (stats.highestGridCount > 1) " (${stats.highestGridCount}x)" else ""
            StatRow(
                label = if (hasPole) "Pole positions" else "Highest grid position",
                value = if (hasPole) stats.highestGridCount.toString()
                else highestGrid?.let { "P$it$highestGridSuffix" } ?: "—",
            )
            Spacer(Modifier.height(8.dp))
            StatRow(label = "DNFs", value = stats.dnfs.toString())
        }
    }
}

@Composable
private fun LabeledValue(label: String, value: String) {
    Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
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
