package com.example.android_popularmovies.data.source.remote

import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.data.source.MovieRemoteDataSource

class MovieRemoteDataSourceImpl(
    private val service: MovieApiService,
) : MovieRemoteDataSource {
    override suspend fun getMovies(): List<MovieApiModel> {
        return service.popularMovies().results
    }

    override suspend fun getMovieDetails(movieId: Int): MovieApiModel {
        return service.movieDetails(movieId)
    }
}