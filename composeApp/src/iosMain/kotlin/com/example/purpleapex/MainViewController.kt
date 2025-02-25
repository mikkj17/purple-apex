package com.example.purpleapex

import androidx.compose.ui.window.ComposeUIViewController
import com.example.purpleapex.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}
