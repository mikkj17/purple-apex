package com.example.purpleapex.constructor.presentation.constructor_detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ConstructorDetailScreenRoot() {
    ConstructorDetailScreen()
}

@Composable
private fun ConstructorDetailScreen() {
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Text(
            "On constructor detail",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
