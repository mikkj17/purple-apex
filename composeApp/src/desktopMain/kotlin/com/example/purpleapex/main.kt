package com.example.purpleapex

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.purpleapex.app.App
import com.example.purpleapex.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Purple Apex",
        ) {
            App()
        }
    }
}