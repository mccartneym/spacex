package uk.co.bits.spacex.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initLogging()
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
        Timber.d("Logging initialised")
    }
}
