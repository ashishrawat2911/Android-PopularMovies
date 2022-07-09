package com.example.android_popularmovies.data.store

import com.example.android_popularmovies.data.mapper.toDBModel
import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.domain.entity.MovieBelongingsEntity
import com.example.android_popularmovies.domain.entity.MovieEntity

class MovieDataStore(
    private val movieLocalDataStore: MovieLocalDataStore,
    private val movieRemoteDataStore: MovieRemoteDataStore,
    private val isNetworkAvailable: Boolean
) {
    suspend fun getMovies(): List<MovieEntity> {
        return if (isNetworkAvailable) {
            return movieRemoteDataStore.getMovies().map { it.toEntity() }.also {
                addMovieToCache(it)
            }
        } else {
            movieLocalDataStore.getMovies().map { it.toEntity() }
        }
    }

    private fun addMovieToCache(movies: List<MovieEntity>) {
        movieLocalDataStore.addMovieToCache(movies.map { it.toDBModel() })
    }

    suspend fun getMovieDetails(movieId: Int): MovieEntity {
        return if (isNetworkAvailable) {
            movieRemoteDataStore.getMovieDetails(movieId).toEntity()
        } else {
            movieLocalDataStore.getMovie(movieId).toEntity()
        }
    }

    suspend fun getMovieBelongings(movieId: Int): List<MovieBelongingsEntity> {
        return movieRemoteDataStore.getMovieBelongings(movieId).results.map { it.toEntity() }
    }
}