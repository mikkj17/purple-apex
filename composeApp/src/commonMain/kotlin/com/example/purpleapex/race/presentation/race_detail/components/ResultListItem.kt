package com.example.purpleapex.race.presentation.race_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.purpleapex.core.presentation.extensions.formatAsPoints
import com.example.purpleapex.race.domain.ResultDetail

@Composable
fun ResultListItem(result: ResultDetail) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = result.positionText, modifier = Modifier.weight(0.6f))
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1.8f),
        ) {
            Text(
                text = result.driver.fullName,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = result.constructor.name,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Text(
            text = result.time?.time ?: result.status,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1.0f),
        )
        Text(
            text = result.points.formatAsPoints(),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.5f),
        )
    }
}
