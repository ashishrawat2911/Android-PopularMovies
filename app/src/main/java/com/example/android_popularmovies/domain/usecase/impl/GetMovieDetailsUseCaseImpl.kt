package com.example.android_popularmovies.domain.usecase.impl

import com.example.android_popularmovies.data.NetworkResult
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieDetailsUseCase {
    override suspend operator fun invoke(movieId: Int): Flow<NetworkResult<MovieDetailDomainModel>> {
        return flow { emit(repository.getMovieDetails(movieId)) }
    }
}
