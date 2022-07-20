package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.data.NetworkResult
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsUseCase {
    suspend operator fun invoke(movieId: Int): Flow<NetworkResult<MovieDetailDomainModel>>
}
