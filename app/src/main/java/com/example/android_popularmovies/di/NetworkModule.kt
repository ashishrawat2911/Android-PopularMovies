package com.example.android_popularmovies.di

import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideService(networkClient: NetworkClient): MovieApiService {
        return networkClient.getRetrofit().create(MovieApiService::class.java)
    }

    @Provides
    fun provideNetworkClient(): NetworkClient = NetworkClient()
}

