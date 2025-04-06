package com.example.purpleapex.circuit.presentation.circuit_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.purpleapex.circuit.domain.Circuit

@Composable
fun CircuitList(
    circuits: List<Circuit>,
    onCircuitClick: (Circuit) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyGridState = rememberLazyGridState(),
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = scrollState,
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        items(
            circuits,
            key = { it.id }
        ) { circuit ->
            CircuitListItem(
                circuit = circuit,
                onClick = { onCircuitClick(circuit) },
                modifier = Modifier
                    .widthIn(max = 512.dp)
                    .fillMaxWidth()
            )
        }
    }
}
