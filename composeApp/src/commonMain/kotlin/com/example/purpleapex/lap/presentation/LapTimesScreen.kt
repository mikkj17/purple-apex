package com.example.purpleapex.lap.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.purpleapex.core.presentation.components.Header

@Composable
fun LapTimesScreenRoot(
    onBackClick: () -> Unit,
) {
    Header(
        onBackClick = onBackClick,
        trailingContent = {
            Text(
                text = "Lap times",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(end = 16.dp),
            )
        }
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Lap times screen (WIP)")
    }
}
