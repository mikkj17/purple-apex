package com.example.purpleapex.driver.presentation.driver_detail.components

import androidx.compose.foundation.BorderStroke
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
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.core.presentation.components.AnimatedContainer
import com.example.purpleapex.driver.domain.DriverDetail

@Composable
fun DriverInfoCard(
    driver: DriverDetail,
    constructors: List<Constructor>,
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
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    LabeledValue(label = "Name", value = driver.fullName)
                    Spacer(Modifier.height(8.dp))
                    if (constructors.isNotEmpty()) {
                        LabeledValue(
                            label = "Constructors",
                            value = constructors.joinToString(separator = ", ") { it.name },
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            LabeledValue(label = "Nationality", value = driver.nationality)
                            LabeledValue(label = "Number", value = driver.number?.toString() ?: "—")
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            LabeledValue(label = "Code", value = driver.code ?: "—")
                            LabeledValue(label = "Date of birth", value = driver.dateOfBirth.toString())
                        }
                    }
                }
            }
        },
        isExpandedByDefault = true,
        modifier = modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
            shape = MaterialTheme.shapes.small,
        ),
    )
}

@Composable
private fun LabeledValue(label: String, value: String) {
    Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
}
