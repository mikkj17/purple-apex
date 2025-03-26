package com.example.purpleapex.core.presentation.extensions

fun Float.formatAsPoints() =
    if (this % 1.0f == 0f) this.toInt().toString()
    else this.toString()
