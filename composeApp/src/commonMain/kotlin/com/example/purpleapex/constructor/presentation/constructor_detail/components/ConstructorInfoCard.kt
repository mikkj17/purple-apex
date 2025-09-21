package com.example.purpleapex.constructor.presentation.constructor_detail.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.core.presentation.components.AnimatedContainer
import com.example.purpleapex.driver.domain.Driver

@Composable
fun ConstructorInfoCard(
    constructor: Constructor,
    drivers: List<Driver>,
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
                tonalElevation = 2.dp,
                modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = MaterialTheme.shapes.small,
                )
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    LabeledValue(label = "Name", value = constructor.name)
                    LabeledValue(label = "Nationality", value = constructor.nationality)
                    Spacer(Modifier.height(8.dp))
                    if (drivers.isNotEmpty()) {
                        LabeledValue(
                            label = "Drivers",
                            value = drivers.joinToString(separator = ", ") { it.fullName },
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        },
        isExpandedByDefault = true,
        modifier = modifier,
    )
}

@Composable
private fun LabeledValue(label: String, value: String) {
    Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
}
