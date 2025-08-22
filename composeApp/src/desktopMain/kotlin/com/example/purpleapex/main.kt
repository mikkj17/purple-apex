package com.example.purpleapex

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.example.purpleapex.app.App
import com.example.purpleapex.di.initKoin

fun main() {
    initKoin()
    singleWindowApplication(
        title = "Purple Apex",
        state = WindowState(width = 400.dp, height = 800.dp),
        alwaysOnTop = true,
    ) {
        App()
    }
}
