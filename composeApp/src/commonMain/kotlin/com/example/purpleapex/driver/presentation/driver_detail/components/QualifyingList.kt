package com.example.purpleapex.driver.presentation.driver_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.qualifying.domain.Qualifying

@Composable
fun QualifyingList(
    qualifyings: List<Qualifying>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        qualifyings.forEach { qual ->
            QualifyingListItem(
                qualifying = qual, modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun QualifyingListItem(
    qualifying: Qualifying,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "${qualifying.season} • Round ${qualifying.round}",
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = "P${qualifying.results.first().position}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        Text(text = qualifying.name, style = MaterialTheme.typography.titleMedium)
        Text(
            text = "${qualifying.circuit.name} — ${qualifying.circuit.location.locality}, ${qualifying.circuit.location.country}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
