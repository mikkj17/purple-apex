package com.example.purpleapex.race.presentation.race_detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.race.domain.RaceDetail

@Composable
fun RaceInfoCard(race: RaceDetail, modifier: Modifier = Modifier) {
    Surface(
        tonalElevation = 1.dp,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "${race.season} • Round ${race.round}",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = race.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = race.circuit.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            val loc = race.circuit.location
            Text(
                text = "${loc.locality}, ${loc.country}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = race.date,
                    style = MaterialTheme.typography.bodySmall,
                )
                race.time?.let { t ->
                    Text(
                        text = t.dropLast(1),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}
