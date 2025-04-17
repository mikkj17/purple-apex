package com.example.purpleapex

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.window.ComposeUIViewController
import com.example.purpleapex.app.App
import com.example.purpleapex.di.initKoin

val LocalNativeViewFactory = staticCompositionLocalOf<NativeViewFactory> {
    error("NativeViewFactory not provided")
}

fun MainViewController(
    nativeViewFactory: NativeViewFactory,
) = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    CompositionLocalProvider(LocalNativeViewFactory provides nativeViewFactory) {
        App()
    }
}
