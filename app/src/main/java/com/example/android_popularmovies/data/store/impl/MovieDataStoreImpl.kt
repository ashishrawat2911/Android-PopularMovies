package com.example.android_popularmovies.data.store.impl

import com.example.android_popularmovies.data.mapper.MovieApiToEntityMapper
import com.example.android_popularmovies.data.mapper.MovieDbToEntityMapper
import com.example.android_popularmovies.data.mapper.MovieEntityToDbMapper
import com.example.android_popularmovies.data.source.MovieLocalDataSource
import com.example.android_popularmovies.data.source.MovieRemoteDataSource
import com.example.android_popularmovies.data.store.MovieDataStore
import com.example.android_popularmovies.domain.entity.MovieEntity

class MovieDataStoreImpl(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieApiToEntityMapper: MovieApiToEntityMapper,
    private val movieDbToEntityMapper: MovieDbToEntityMapper,
    private val movieEntityToDbMapper: MovieEntityToDbMapper,
    private val isNetworkAvailable: Boolean
) : MovieDataStore {
    override suspend fun getMovies(): List<MovieEntity> {
        return if (isNetworkAvailable) {
            return movieRemoteDataSource.getMovies().map {
                movieApiToEntityMapper.map(it)
            }.also {
                addMovieToCache(it)
            }
        } else {
            movieLocalDataSource.getMovies().map { movieDbToEntityMapper.map(it) }
        }
    }

    private fun addMovieToCache(movies: List<MovieEntity>) {
        movieLocalDataSource.addMovieToCache(movies.map { movieEntityToDbMapper.map(it) })
    }

    override suspend fun getMovieDetails(movieId: Int): MovieEntity {
        return if (isNetworkAvailable) {
            movieApiToEntityMapper.map(movieRemoteDataSource.getMovieDetails(movieId))
        } else {
            movieDbToEntityMapper.map(movieLocalDataSource.getMovie(movieId))
        }
    }
}