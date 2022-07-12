package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.domain.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface GetMoviesUseCase {
    suspend operator fun invoke(): Flow<List<MovieEntity>>
}


