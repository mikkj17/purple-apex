package com.example.purpleapex.qualifying.presentation.qualifying_detail

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
fun QualifyingDetailScreenRoot(
    onBackClick: () -> Unit,
) {
    Header(
        onBackClick = onBackClick,
        trailingContent = {
            Text(
                text = "Qualifying",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(end = 16.dp),
            )
        }
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Qualifying details screen (WIP)")
    }
}
