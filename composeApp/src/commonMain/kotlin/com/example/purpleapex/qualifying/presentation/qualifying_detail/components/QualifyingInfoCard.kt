package com.example.purpleapex.qualifying.presentation.qualifying_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.core.presentation.components.AppCard
import com.example.purpleapex.qualifying.domain.QualifyingDetail

@Composable
fun QualifyingInfoCard(qualifying: QualifyingDetail, modifier: Modifier = Modifier) {
    AppCard(
        shape = MaterialTheme.shapes.small,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "${qualifying.season} • Round ${qualifying.round}",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = qualifying.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = qualifying.circuit.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            val loc = qualifying.circuit.location
            Text(
                text = "${loc.locality}, ${loc.country}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = qualifying.date,
                    style = MaterialTheme.typography.bodySmall,
                )
                qualifying.time?.let { t ->
                    Text(
                        text = t.dropLast(1),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}
