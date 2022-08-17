package com.example.android_popularmovies.data.source.remote

import com.example.android_popularmovies.data.model.MovieResponseModel
import com.example.android_popularmovies.data.source.MovieRemoteDataSource
import com.example.android_popularmovies.data.source.remote.service.MovieApiService
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val service: MovieApiService,
) : MovieRemoteDataSource {
    override suspend fun getMovies(): List<MovieResponseModel> {
        return service.popularMovies().results
    }

    override suspend fun getMovieDetails(movieId: Int): MovieResponseModel {
        return service.movieDetails(movieId)
    }
}