package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.utils.NetworkResult
import com.example.android_popularmovies.domain.model.MovieDomainModel
import kotlinx.coroutines.flow.Flow

interface GetMoviesUseCase {
    suspend operator fun invoke(): Flow<NetworkResult<List<MovieDomainModel>>>
}
