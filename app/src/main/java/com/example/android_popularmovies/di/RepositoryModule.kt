package com.example.android_popularmovies.di

import com.example.android_popularmovies.data.repository.MovieRepositoryImpl
import com.example.android_popularmovies.data.source.MovieLocalDataSource
import com.example.android_popularmovies.data.source.MovieRemoteDataSource
import com.example.android_popularmovies.data.source.local.MovieLocalDataSourceImpl
import com.example.android_popularmovies.data.source.remote.MovieRemoteDataSourceImpl
import com.example.android_popularmovies.data.store.MovieDataStore
import com.example.android_popularmovies.data.store.impl.MovieDataStoreImpl
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
    abstract fun bindsMovieDataStore(movieDataStoreImpl: MovieDataStoreImpl): MovieDataStore
    @Binds
    abstract fun bindsMovieLocalDataSource(movieLocalDataSourceImpl: MovieLocalDataSourceImpl): MovieLocalDataSource
    @Binds
    abstract fun bindsMovieRemoteDataSource(movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRemoteDataSource
}
