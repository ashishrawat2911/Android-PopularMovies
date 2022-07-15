package com.example.android_popularmovies.data.store.impl

import com.example.android_popularmovies.data.mapper.MovieApiToEntityMapper
import com.example.android_popularmovies.data.source.MovieRemoteDataSource
import com.example.android_popularmovies.data.store.MovieDataStore
import com.example.android_popularmovies.domain.entity.MovieEntity

class MovieDataStoreImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieApiToEntityMapper: MovieApiToEntityMapper,
) : MovieDataStore {
    override suspend fun getMovies(): List<MovieEntity> {
        return movieRemoteDataSource.getMovies().map {
            movieApiToEntityMapper.map(it)
        }
    }
    override suspend fun getMovieDetails(movieId: Int): MovieEntity {
        return movieApiToEntityMapper.map(movieRemoteDataSource.getMovieDetails(movieId))
    }
}