package com.example.android_popularmovies.domain.repository

import com.example.android_popularmovies.data.NetworkResult
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.domain.model.MovieDomainModel

interface MovieRepository {
    suspend fun getMovies(): NetworkResult<List<MovieDomainModel>>

    suspend fun getMovieDetails(movieId: Int): NetworkResult<MovieDetailDomainModel>
}
