package com.example.purpleapex.race.presentation.race_detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
fun RaceDetailScreenRoot() {
    RaceDetailScreen()
}

@Composable
private fun RaceDetailScreen() {
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        TelemetryChart()
    }
}

@Composable
expect fun TelemetryChart()
