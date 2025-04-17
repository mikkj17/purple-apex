package com.example.purpleapex.core.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import com.example.purpleapex.LocalNativeViewFactory

@Composable
actual fun TelemetryChart() {
    val factory = LocalNativeViewFactory.current
    UIKitViewController(
        modifier = Modifier.fillMaxSize(),
        factory = {
            factory.createChartView(emptyMap())
        }
    )
}
