package com.example.purpleapex.grandprix.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.core.presentation.components.AppCard
import com.example.purpleapex.schedule.domain.ScheduleDetail

@Composable
fun GrandPrixInfoCard(schedule: ScheduleDetail, modifier: Modifier = Modifier) {
    AppCard(
        shape = MaterialTheme.shapes.small,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "${schedule.season} • Round ${schedule.round}",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = schedule.raceName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = schedule.circuit.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            val loc = schedule.circuit.location
            Text(
                text = "${loc.locality}, ${loc.country}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = schedule.date,
                    style = MaterialTheme.typography.bodySmall,
                )
                schedule.time?.let { t ->
                    Text(
                        text = t.dropLast(1),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}
