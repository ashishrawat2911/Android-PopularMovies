package com.example.android_popularmovies.data.source.remote

import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.domain.model.MovieDomainModel

interface MovieDataService {
    suspend fun getMovies(): List<MovieDomainModel>

    suspend fun getMovieDetails(movieId: Int): MovieDetailDomainModel
}
