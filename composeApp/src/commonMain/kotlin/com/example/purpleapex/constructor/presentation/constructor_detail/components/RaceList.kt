package com.example.purpleapex.constructor.presentation.constructor_detail.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        races.forEach { race ->
            RaceListItem(race = race, modifier = modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun RaceListItem(
    race: Race,
    modifier: Modifier = Modifier,
) {
    Surface(
        tonalElevation = 1.dp,
        modifier = Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface,
            shape = MaterialTheme.shapes.small,
        )
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(text = "${race.season} • Round ${race.round}", style = MaterialTheme.typography.labelMedium)
            Text(text = race.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = race.circuit.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                race.results.forEach { result ->
                    Surface(
                        tonalElevation = 3.dp,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSurface,
                                shape = MaterialTheme.shapes.small,
                            )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = result.driver.fullName,
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Text(
                                text = "P${result.position}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    }
}
