package com.example.android_popularmovies.data.source

import com.example.android_popularmovies.data.model.MovieResponseModel

interface MovieRemoteDataSource {
    suspend fun getMovies(): List<MovieResponseModel>
    suspend fun getMovieDetails(movieId: Int): MovieResponseModel
}

