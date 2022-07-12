package com.example.android_popularmovies.data.store

import com.example.android_popularmovies.domain.entity.MovieEntity

interface MovieDataStore {
    suspend fun getMovies(): List<MovieEntity>

    suspend fun getMovieDetails(movieId: Int): MovieEntity
}

