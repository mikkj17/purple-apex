package com.example.purpleapex.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedContainer(
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
    isExpandedByDefault: Boolean = false,
    modifier: Modifier = Modifier,
) {
    var expanded by remember {
        mutableStateOf(isExpandedByDefault)
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
            ) {
                header()
            }
            AnimatedVisibility(
                visible = expanded,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                content()
            }
        }
    }
}
