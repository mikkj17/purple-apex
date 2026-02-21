package com.example.purpleapex.di

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Js.create() }
    }
