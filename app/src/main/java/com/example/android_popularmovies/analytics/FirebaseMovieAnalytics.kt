package com.example.android_popularmovies.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseMovieAnalytics(var firebaseAnalytics: FirebaseAnalytics) : MovieAnalytics {
    override fun logEvent(name: String, bundle: Bundle) {
        firebaseAnalytics.logEvent(name, bundle)
    }
}