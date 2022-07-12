package com.example.android_popularmovies.data.repository

import com.example.android_popularmovies.data.store.MovieDataStore
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val movieDataStore: MovieDataStore,
) : MovieRepository {

    override suspend fun getMovies(): List<MovieEntity> {
        return movieDataStore.getMovies()
    }

    override suspend fun getMovieDetails(movieId: Int): MovieEntity {
        return  movieDataStore.getMovieDetails(movieId)
    }
}
