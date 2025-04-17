package com.example.purpleapex.race.presentation.race_detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
        Text(
            "On race detail",
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}
