package com.example.android_popularmovies.di

import com.example.android_popularmovies.data.mapper.MovieApiToEntityMapper
import com.example.android_popularmovies.data.mapper.MovieDbToEntityMapper
import com.example.android_popularmovies.data.mapper.MovieEntityToDbMapper
import com.example.android_popularmovies.data.repository.MovieRepositoryImpl
import com.example.android_popularmovies.data.source.MovieLocalDataSource
import com.example.android_popularmovies.data.source.MovieRemoteDataSource
import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.local.MovieLocalDataSourceImpl
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.MovieRemoteDataSourceImpl
import com.example.android_popularmovies.data.source.remote.NetworkClient
import com.example.android_popularmovies.data.store.MovieDataStore
import com.example.android_popularmovies.data.store.impl.MovieDataStoreImpl
import com.example.android_popularmovies.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideService(networkClient: NetworkClient): MovieApiService {
        return networkClient.getRetrofit().create(MovieApiService::class.java)
    }

    @Provides
    fun provideNetworkClient(): NetworkClient = NetworkClient()

    @Singleton
    @Provides
    fun provideMovieRemoteDataStore(
        retrofitService: MovieApiService,
    ): MovieRemoteDataSource = MovieRemoteDataSourceImpl(
        retrofitService
    )

    @Singleton
    @Provides
    fun provideMovieLocalDataStore(
        movieDao: MovieDao
    ): MovieLocalDataSource = MovieLocalDataSourceImpl(
        movieDao,
    )

    @Singleton
    @Provides
    fun provideMovieDataStore(
        movieLocalDataSource: MovieLocalDataSource,
        movieRemoteDataSource: MovieRemoteDataSource,
    ): MovieDataStore {
        return MovieDataStoreImpl(
            movieLocalDataSource = movieLocalDataSource,
            movieRemoteDataSource = movieRemoteDataSource,
            movieApiToEntityMapper = MovieApiToEntityMapper(),
            movieDbToEntityMapper = MovieDbToEntityMapper(),
            movieEntityToDbMapper = MovieEntityToDbMapper(),
        )
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieDataStore: MovieDataStore
    ): MovieRepository = MovieRepositoryImpl(
        movieDataStore,
    )
}
