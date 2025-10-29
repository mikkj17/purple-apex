package com.example.purpleapex.core.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.letsPlot.Figure
import org.jetbrains.letsPlot.Stat
import org.jetbrains.letsPlot.compose.PlotPanel
import org.jetbrains.letsPlot.geom.geomLine
import org.jetbrains.letsPlot.letsPlot

@Composable
actual fun TelemetryChart() {
    PlotPanel(
        figure = createFigure(),
        preserveAspectRatio = true,
        modifier = Modifier.fillMaxSize(),
    ) {
        it.forEach { println("[DEMO APP MESSAGE] $it") }
    }
}

private fun createFigure(): Figure {
    val nums = (1..5).toList()
    val data = mapOf(
        "x" to nums,
        "squared" to nums.map { it * it },
    )

    return letsPlot(data) + geomLine(stat = Stat.identity) { x = "x"; y = "squared" }
}
