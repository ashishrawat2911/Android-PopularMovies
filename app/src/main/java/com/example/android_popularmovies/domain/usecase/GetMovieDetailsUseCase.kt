package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.domain.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsUseCase {
    suspend operator fun invoke(movieId: Int): Flow<MovieEntity>
}

