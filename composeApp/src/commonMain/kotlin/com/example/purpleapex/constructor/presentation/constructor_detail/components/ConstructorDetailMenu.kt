package com.example.purpleapex.constructor.presentation.constructor_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.purpleapex.constructor.presentation.constructor_detail.ConstructorDetailAction

@Composable
fun ConstructorDetailMenu(
    onAction: (ConstructorDetailAction) -> Unit,
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
                    onAction(ConstructorDetailAction.OnHistoryClick("")) // The ID will be filled in the ViewModel or Action handler if needed, but here we just trigger the action
                }
            )
        }
    }
}
