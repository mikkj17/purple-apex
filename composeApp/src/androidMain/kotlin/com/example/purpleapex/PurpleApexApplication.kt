package com.example.purpleapex

import android.app.Application
import com.example.purpleapex.di.initKoin
import org.koin.android.ext.koin.androidContext

class PurpleApexApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@PurpleApexApplication)
        }
    }
}
