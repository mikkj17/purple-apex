package com.example.purpleapex

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform