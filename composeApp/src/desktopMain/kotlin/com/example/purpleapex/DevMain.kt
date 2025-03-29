package com.example.purpleapex

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.example.purpleapex.app.App
import com.example.purpleapex.di.initKoin
import org.jetbrains.compose.reload.DevelopmentEntryPoint

fun main() {
    initKoin()
    singleWindowApplication(
        title = "Hot reload",
        state = WindowState(width = 400.dp, height = 800.dp),
        alwaysOnTop = true,
    ) {
        DevelopmentEntryPoint {
            App()
        }
    }
}
