package com.example.purpleapex

import platform.UIKit.UIViewController

interface NativeViewFactory {
    fun createChartView(
        data: Map<String, List<Int>>,
    ): UIViewController
}
