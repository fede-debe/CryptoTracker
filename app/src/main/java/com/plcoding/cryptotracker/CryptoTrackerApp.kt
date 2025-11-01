package com.plcoding.cryptotracker

import android.app.Application
import com.plcoding.cryptotracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Application class. A class that is called when the app is starting.
 * We can setup koin here. androidContext is provided in case one of the
 * dependencies needs the application context to be created.
 *
 * With a feature module, we should have a single isolated module for it.
 * */
class CryptoTrackerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoTrackerApp)
            androidLogger()

            modules(appModule)
        }
    }
}