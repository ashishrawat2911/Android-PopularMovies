package com.example.android_popularmovies.di

import android.app.Application
import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): MovieDatabase {
        return MovieDatabase.getInstance(application)
    }

    @Provides
    internal fun provideMovieDao(appDatabase: MovieDatabase): MovieDao {
        return appDatabase.movieDao()
    }
}
