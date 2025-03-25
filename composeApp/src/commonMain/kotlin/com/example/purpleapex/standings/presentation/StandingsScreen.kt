package com.example.purpleapex.standings.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun StandingsScreenRoot() {
    StandingsScreen()
}

@Composable
private fun StandingsScreen() {
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Text(
            "On standings screen",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
