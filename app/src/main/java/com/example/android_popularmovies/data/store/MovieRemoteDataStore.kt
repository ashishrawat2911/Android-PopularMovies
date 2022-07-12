package com.example.android_popularmovies.data.store

import com.example.android_popularmovies.data.source.remote.model.MovieApiModel

interface MovieRemoteDataStore {
    suspend fun getMovies(): List<MovieApiModel>
    suspend fun getMovieDetails(movieId: Int): MovieApiModel
}

