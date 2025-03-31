package com.example.purpleapex.core.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.purpleapex.core.util.Constants
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeasonDropdown(
    selectedYear: Int,
    onSelectedYearChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentYear = Clock.System.todayIn(TimeZone.UTC).year
    val years = (currentYear downTo Constants.FIRST_F1_SEASON)
    val expanded = remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value },
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable)
                .width(80.dp)
                .border(
                    width = 1.5.dp,
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                .padding(vertical = 4.dp, horizontal = 12.dp)
        ) {
            Text(
                selectedYear.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.exposedDropdownSize()
        ) {
            years.forEach { year ->
                DropdownMenuItem(
                    text = { Text(year.toString()) },
                    onClick = {
                        onSelectedYearChanged(year)
                        expanded.value = false
                    }
                )
            }
        }
    }
}
