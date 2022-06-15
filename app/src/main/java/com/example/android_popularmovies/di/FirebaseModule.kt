package com.example.android_popularmovies.di

import android.content.Context
import com.example.android_popularmovies.analytics.FirebaseMovieAnalytics
import com.example.android_popularmovies.analytics.MovieAnalytics
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FirebaseModule {
    @Provides
    @Singleton
    fun providesMovieAnalytics(@ApplicationContext context: Context): MovieAnalytics {
        return FirebaseMovieAnalytics(FirebaseAnalytics.getInstance(context))
    }
}
