package com.example.purpleapex.race.presentation.race_detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.purpleapex.race.domain.ResultDetail

@Composable
fun ResultList(results: List<ResultDetail>) {
    Surface(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp),
        ) {
            Row {
                Text(
                    text = "Pos.",
                    modifier = Modifier.weight(0.6f),
                )
                Text(
                    text = "Driver",
                    modifier = Modifier.weight(1.8f),
                )
                Text(
                    text = "Time",
                    modifier = Modifier.weight(1.0f),
                    textAlign = TextAlign.End,
                )
                Text(
                    text = "Pts.",
                    modifier = Modifier.weight(0.5f),
                    textAlign = TextAlign.End,
                )
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
            )
            results.forEach { result ->
                ResultListItem(result = result)
            }
        }
    }
}
