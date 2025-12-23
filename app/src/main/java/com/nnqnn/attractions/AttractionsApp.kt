package com.nnqnn.attractions

import android.app.Application
import com.nnqnn.attractions.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AttractionsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AttractionsApp)
            modules(appModule)
        }
    }
}

