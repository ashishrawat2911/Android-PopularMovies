package com.example.android_popularmovies.data.store.impl

import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.data.store.MovieRemoteDataStore

class MovieRemoteDataStoreImpl(
    private val service: MovieApiService,
) : MovieRemoteDataStore {
    override suspend fun getMovies(): List<MovieApiModel> {
        return service.popularMovies().results
    }

    override suspend fun getMovieDetails(movieId: Int): MovieApiModel {
        return service.movieDetails(movieId)
    }
}