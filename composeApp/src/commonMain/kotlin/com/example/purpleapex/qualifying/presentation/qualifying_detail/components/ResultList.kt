package com.example.purpleapex.qualifying.presentation.qualifying_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.purpleapex.core.presentation.components.AppCard
import com.example.purpleapex.qualifying.domain.ResultDetail

@Composable
fun ResultList(
    results: List<ResultDetail>,
) {
    AppCard {
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
                    text = "Q1",
                    modifier = Modifier.weight(1.0f),
                    textAlign = TextAlign.End,
                )
                Text(
                    text = "Q2",
                    modifier = Modifier.weight(1.0f),
                    textAlign = TextAlign.End,
                )
                Text(
                    text = "Q3",
                    modifier = Modifier.weight(1.0f),
                    textAlign = TextAlign.End,
                )
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
            )
            results.forEachIndexed { index, result ->
                ResultListItem(
                    position = index + 1,
                    result = result,
                )
            }
        }
    }
}
