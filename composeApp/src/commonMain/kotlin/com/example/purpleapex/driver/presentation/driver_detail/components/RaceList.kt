package com.example.purpleapex.driver.presentation.driver_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.driver.domain.DriverDetail
import com.example.purpleapex.race.domain.Race

@Composable
fun RaceList(
    races: List<Race>,
    driver: DriverDetail,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                races.forEach { race ->
                    RaceListItem(
                        race = race, driverId = driver.id,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun RaceListItem(
    race: Race,
    driverId: String,
    modifier: Modifier = Modifier,
) {
    Surface(shape = RoundedCornerShape(8.dp), modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "${race.season} • Round ${race.round}", style = MaterialTheme.typography.labelMedium)
                val myResult = race.results.firstOrNull { it.driver.id == driverId }
                Text(
                    text = myResult?.let { "P${it.position}" } ?: "—",
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
}
