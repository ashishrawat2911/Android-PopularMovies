package com.example.android_popularmovies.domain.usecase.impl

import com.example.android_popularmovies.data.NetworkResult
import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMoviesUseCase {
    override suspend fun invoke(): Flow<NetworkResult<List<MovieDomainModel>>> {
        return flow { emit(repository.getMovies()) }
    }
}
