package com.example.purpleapex.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
actual fun TelemetryChart() {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                val entries = (1..5).map { Entry(it.toFloat(), (it * it).toFloat()) }
                val dataSet = LineDataSet(entries, "Squared Values").apply {
                    lineWidth = 2f
                    circleRadius = 4f
                }
                data = LineData(dataSet)
                description.isEnabled = false
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}
