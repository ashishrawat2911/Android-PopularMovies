package com.example.android_popularmovies.data.store

import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.domain.model.MovieDomainModel

interface MovieDataStore {
    suspend fun getMovies(): List<MovieDomainModel>

    suspend fun getMovieDetails(movieId: Int): MovieDetailDomainModel
}

