package com.example.android_popularmovies

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant


@HiltAndroidApp
class MainApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        applicationContext()
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
        setupStrictPolicy()
    }

    private fun setupStrictPolicy() {
        StrictMode.setThreadPolicy(
            ThreadPolicy.Builder()
                .penaltyLog()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .build()
        )
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }
}