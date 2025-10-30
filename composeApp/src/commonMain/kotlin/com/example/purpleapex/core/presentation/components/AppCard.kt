package com.example.purpleapex.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * AppCard is a reusable container that renders identically structured cards
 * for both light and dark modes while adapting visual depth cues:
 * - Light: soft shadow elevation
 * - Dark: subtle top highlight and faint hairline border (no drop shadow)
 */
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerLow,
    elevationLight: Dp = 3.dp,
    highlightHeightDark: Dp = 24.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    val isDark = isSystemInDarkTheme()

    val border = if (isDark) {
        BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.24f)
        )
    } else null

    Surface(
        shape = shape,
        color = containerColor,
        shadowElevation = if (isDark) 0.dp else elevationLight,
        border = border,
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
        ) {
            // Content first
            content()

            // Dark-mode top highlight overlay
            if (isDark) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .height(highlightHeightDark)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.08f),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }
        }
    }
}
