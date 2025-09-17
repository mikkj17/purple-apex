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
import com.example.purpleapex.race.domain.Race

@Composable
fun RaceList(
    races: List<Race>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        races.forEach { race ->
            RaceListItem(
                race = race,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun RaceListItem(
    race: Race,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "${race.season} • Round ${race.round}", style = MaterialTheme.typography.labelMedium)
            Text(
                text = "P${race.results.first().position}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        Text(text = race.name, style = MaterialTheme.typography.titleMedium)
        Text(
            text = "${race.circuit.name} — ${race.circuit.location.locality}, ${race.circuit.location.country}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
