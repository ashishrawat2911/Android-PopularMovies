package com.example.android_popularmovies.di

import android.app.Application
import com.example.android_popularmovies.data.source.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideAppDatabase(application: Application) =
        MovieDatabase.getInstance(application)

    @Provides
    fun provideMovieDao(appDatabase: MovieDatabase) = appDatabase.movieDao()
}
