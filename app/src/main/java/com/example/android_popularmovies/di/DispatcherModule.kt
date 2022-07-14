package com.example.android_popularmovies.di

import com.example.android_popularmovies.utils.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DispatcherModule {
    @Provides
    @Singleton
    fun providesAppDispatchers(): AppDispatchers {
        return AppDispatchers()
    }
}
