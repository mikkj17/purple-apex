package com.example.purpleapex.race.presentation.race_list

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RaceListScreenRoot() {
    RaceListScreen()
}

@Composable
private fun RaceListScreen() {
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Text(
            "On race screen",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
