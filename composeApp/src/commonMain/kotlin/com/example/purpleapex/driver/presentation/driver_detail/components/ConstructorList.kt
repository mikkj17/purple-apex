package com.example.purpleapex.driver.presentation.driver_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.purpleapex.constructor.domain.Constructor

@Composable
fun ConstructorList(
    constructors: List<Constructor>,
    onConstructorClick: (Constructor) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            "Constructors",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onTertiary,
        )
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(vertical = 8.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                constructors.forEach { constructor ->
                    ConstructorListItem(
                        constructor = constructor,
                        onClick = { onConstructorClick(constructor) },
                        modifier = Modifier
                            .widthIn(max = 512.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
