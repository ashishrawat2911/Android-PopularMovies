package com.example.android_popularmovies.data.source

import com.example.android_popularmovies.data.source.remote.model.MovieApiModel

interface MovieRemoteDataSource {
    suspend fun getMovies(): List<MovieApiModel>
    suspend fun getMovieDetails(movieId: Int): MovieApiModel
}

