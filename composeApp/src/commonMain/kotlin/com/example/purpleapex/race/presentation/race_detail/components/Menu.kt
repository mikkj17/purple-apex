package com.example.purpleapex.race.presentation.race_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.purpleapex.race.presentation.race_detail.RaceDetailAction

@Composable
fun Menu(onAction: (RaceDetailAction) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = "More",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Qualifying") },
                onClick = {
                    expanded = false
                    onAction(RaceDetailAction.OnQualifyingClick)
                }
            )
            DropdownMenuItem(
                text = { Text("Lap times") },
                onClick = {
                    expanded = false
                    onAction(RaceDetailAction.OnLapTimesClick)
                }
            )
        }
    }
}
