package com.example.purpleapex.result.presentation.result_list

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ResultListScreenRoot() {
    ResultListScreen()
}

@Composable
private fun ResultListScreen() {
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Text(
            "On results screen",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
