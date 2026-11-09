package com.example.purpleapex.driver.presentation.driver_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.purpleapex.driver.presentation.driver_detail.DriverDetailAction

@Composable
fun DriverDetailMenu(
    onAction: (DriverDetailAction) -> Unit,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = "More",
                tint = tint,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Racing History") },
                onClick = {
                    expanded = false
                    onAction(DriverDetailAction.OnHistoryClick)
                }
            )
        }
    }
}
