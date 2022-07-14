package com.example.android_popularmovies.domain.usecase.impl

import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieDetailsUseCaseImpl constructor(
    private val repository: MovieRepository
) : GetMovieDetailsUseCase {
    override suspend operator fun invoke(movieId: Int): Flow<MovieEntity> {
        return flow { emit(repository.getMovieDetails(movieId)) }
    }
}