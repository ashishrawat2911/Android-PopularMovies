package com.example.android_popularmovies.domain.usecase.impl

import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMoviesUseCaseImpl constructor(
    private val repository: MovieRepository
) : GetMoviesUseCase {
    override suspend fun invoke(): Flow<List<MovieEntity>> {
        return flow { emit(repository.getMovies()) }
    }
}