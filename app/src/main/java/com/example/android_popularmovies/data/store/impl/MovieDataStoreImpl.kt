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
) : MovieDataStore {
    override suspend fun getMovies(): List<MovieEntity> {
        val moviesFromDB = movieLocalDataSource.getMovies().map { movieDbToEntityMapper.map(it) }
        return moviesFromDB.ifEmpty {
            movieRemoteDataSource.getMovies().map {
                movieApiToEntityMapper.map(it)
            }.also {
                addMovieToCache(it)
            }
        }

    }

    private fun addMovieToCache(movies: List<MovieEntity>) {
        movieLocalDataSource.setMoviesToCache(movies.map { movieEntityToDbMapper.map(it) })
    }

    override suspend fun getMovieDetails(movieId: Int): MovieEntity {
        return movieDbToEntityMapper.map(movieLocalDataSource.getMovie(movieId))
    }
}