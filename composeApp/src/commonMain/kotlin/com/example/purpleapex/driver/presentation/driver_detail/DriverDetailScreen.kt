package com.example.purpleapex.driver.presentation.driver_detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DriverDetailScreenRoot() {
    DriverDetailScreen()
}

@Composable
fun DriverDetailScreen() {
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Text(
            "On driver detail",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
