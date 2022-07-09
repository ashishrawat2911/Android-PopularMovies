package com.example.android_popularmovies.data.store

import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingList

class MovieRemoteDataStore(
    private val service: MovieApiService,
) {
    suspend fun getMovies(): List<MovieApiModel> {
        return service.popularMovies().results
    }

    suspend fun getMovieDetails(movieId: Int): MovieApiModel {
        return service.movieDetails(movieId)
    }

    suspend fun getMovieBelongings(movieId: Int): MovieBelongingList {
        return service.movieBelongings(movieId)
    }
}