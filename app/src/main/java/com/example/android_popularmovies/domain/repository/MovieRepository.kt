package com.example.android_popularmovies.domain.repository

import com.example.android_popularmovies.domain.entity.MovieEntity

interface MovieRepository {
    suspend fun getMovies(): List<MovieEntity>
    suspend fun getMovieDetails(movieId: Int): MovieEntity
}
