package com.example.android_popularmovies.analytics

import android.os.Bundle

interface MovieAnalytics {
    fun logEvent(name: String, bundle: Bundle)
}

