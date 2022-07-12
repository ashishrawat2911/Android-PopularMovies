package com.example.android_popularmovies.di

import android.content.Context
import com.example.android_popularmovies.data.mapper.MovieApiToEntityMapper
import com.example.android_popularmovies.data.mapper.MovieDbToEntityMapper
import com.example.android_popularmovies.data.mapper.MovieEntityToDbMapper
import com.example.android_popularmovies.data.repository.MovieRepositoryImpl
import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.NetworkClient
import com.example.android_popularmovies.data.store.MovieDataStore
import com.example.android_popularmovies.data.store.MovieLocalDataStore
import com.example.android_popularmovies.data.store.MovieRemoteDataStore
import com.example.android_popularmovies.data.store.impl.MovieDataStoreImpl
import com.example.android_popularmovies.data.store.impl.MovieLocalDataStoreImpl
import com.example.android_popularmovies.data.store.impl.MovieRemoteDataStoreImpl
import com.example.android_popularmovies.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideIsNetworkAvailable(
        @ApplicationContext context: Context,
        networkClient: NetworkClient
    ): Boolean = networkClient.isNetworkAvailable(context)

    @Singleton
    @Provides
    fun provideMovieRemoteDataStore(
        retrofitService: MovieApiService,
    ): MovieRemoteDataStore = MovieRemoteDataStoreImpl(
        retrofitService
    )

    @Singleton
    @Provides
    fun provideMovieLocalDataStore(
        movieDao: MovieDao
    ): MovieLocalDataStore = MovieLocalDataStoreImpl(
        movieDao,
    )

    @Singleton
    @Provides
    fun provideMovieDataStore(
        movieLocalDataStore: MovieLocalDataStore,
        movieRemoteDataStore: MovieRemoteDataStore,
        isNetworkAvailable: Boolean
    ): MovieDataStore {
        return MovieDataStoreImpl(
            movieLocalDataStore = movieLocalDataStore,
            movieRemoteDataStore = movieRemoteDataStore,
            isNetworkAvailable = isNetworkAvailable,
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
