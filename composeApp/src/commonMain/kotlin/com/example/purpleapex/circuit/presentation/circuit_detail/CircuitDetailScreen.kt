package com.example.purpleapex.circuit.presentation.circuit_detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CircuitDetailScreenRoot() {
    CircuitDetailScreen()
}

@Composable
private fun CircuitDetailScreen() {
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Text(
            "On circuit detail",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
