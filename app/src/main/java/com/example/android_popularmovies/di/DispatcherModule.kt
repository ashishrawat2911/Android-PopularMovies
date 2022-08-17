package com.example.android_popularmovies.di

import com.example.android_popularmovies.utils.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DispatcherModule {
    @Provides
    fun providesAppDispatchers(): AppDispatchers {
        return AppDispatchers()
    }
}
