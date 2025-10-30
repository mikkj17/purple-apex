package com.example.purpleapex.constructor.presentation.constructor_detail.components

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

@Composable
fun ConstructorInfoCard(
    constructor: Constructor,
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
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column {
                        LabeledValue(label = "Name", value = constructor.name)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        LabeledValue(label = "Nationality", value = constructor.nationality)
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
