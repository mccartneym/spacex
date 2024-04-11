package uk.co.bits.spacex.ui

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogging()
        enableStrictMode()
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
        Timber.d("Logging initialised")
    }

    private fun enableStrictMode() {
        Timber.v("enableStrictMode")
        val threadPolicy = StrictMode.ThreadPolicy.Builder()
            .detectDiskReads()
            .detectDiskWrites()
            .detectAll()
//            .detectNetwork()   // or .detectAll() for all detectable problems
            .penaltyFlashScreen()
            .penaltyDeath()
            .penaltyLog()
            .build()

        StrictMode.setThreadPolicy(threadPolicy)

        val vmPolicy = StrictMode.VmPolicy.Builder()
            .detectLeakedSqlLiteObjects()
//            .detectLeakedClosableObjects()
            .penaltyLog()
            .penaltyDeath()
            .build()

        StrictMode.setVmPolicy(vmPolicy)
    }
}
