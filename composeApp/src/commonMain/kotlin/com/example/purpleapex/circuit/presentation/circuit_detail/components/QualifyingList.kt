package com.example.purpleapex.circuit.presentation.circuit_detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.qualifying.domain.Qualifying

@Composable
fun QualifyingList(
    qualifyings: List<Qualifying>,
    modifier: Modifier = Modifier,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        qualifyings.forEach { qual ->
            QualifyingListItem(qualifying = qual, modifier = modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun QualifyingListItem(
    qualifying: Qualifying,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                text = "${qualifying.season} • Round ${qualifying.round}",
                style = MaterialTheme.typography.labelMedium,
            )
            Text(text = qualifying.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = qualifying.circuit.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                qualifying.results.take(3).forEach { result ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        ),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column {
                                Text(
                                    text = result.driver.fullName,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                                Text(
                                    text = result.constructor.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            Text(
                                text = "P${result.position}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    }
}
