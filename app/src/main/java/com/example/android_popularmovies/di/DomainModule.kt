package com.example.android_popularmovies.di

import com.example.android_popularmovies.domain.usecase.FilterMoviesUseCase
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import com.example.android_popularmovies.domain.usecase.impl.FilterMoviesUseCaseImpl
import com.example.android_popularmovies.domain.usecase.impl.GetMovieDetailsUseCaseImpl
import com.example.android_popularmovies.domain.usecase.impl.GetMoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DomainModule {
    @Binds
    abstract fun bindsGetMoviesUseCase(getMoviesUseCaseImpl: GetMoviesUseCaseImpl): GetMoviesUseCase

    @Binds
    abstract fun bindsGetMoviesDetailsUseCase(getMovieDetailsUseCaseImpl: GetMovieDetailsUseCaseImpl): GetMovieDetailsUseCase

    @Binds
    abstract fun bindsFilterMoviesUseCase(filterMoviesUseCaseImpl: FilterMoviesUseCaseImpl): FilterMoviesUseCase
}
