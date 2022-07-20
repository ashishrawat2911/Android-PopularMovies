package com.example.android_popularmovies.di

import com.example.android_popularmovies.data.repository.MovieRepositoryImpl
import com.example.android_popularmovies.data.source.remote.MovieDataService
import com.example.android_popularmovies.data.source.remote.impl.MovieDataServiceImpl
import com.example.android_popularmovies.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindsMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindsMovieDataService(movieDataServiceImpl: MovieDataServiceImpl): MovieDataService
}
