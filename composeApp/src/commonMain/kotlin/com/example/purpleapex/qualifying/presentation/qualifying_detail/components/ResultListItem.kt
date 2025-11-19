package com.example.purpleapex.qualifying.presentation.qualifying_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.purpleapex.qualifying.domain.ResultDetail

@Composable
fun ResultListItem(position: Int, result: ResultDetail) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = position.toString(), modifier = Modifier.weight(0.6f))
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
            text = result.q3 ?: result.q2 ?: result.q1 ?: "–",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1.0f),
        )
    }
}
