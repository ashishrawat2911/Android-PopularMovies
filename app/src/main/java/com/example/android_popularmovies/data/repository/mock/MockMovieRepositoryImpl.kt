package com.example.android_popularmovies.data.repository.mock

import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.domain.entity.MovieBelongingsEntity
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository

class MockMovieRepositoryImpl : MovieRepository {
    override suspend fun getMovies(): List<MovieEntity> {
        return MockMovies.generateMovieListModel(10).results!!.map { it.toEntity() }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieEntity {
        return MockMovies.generateMovie().toEntity()
    }

    override suspend fun getMovieBelongings(movieId: Int): List<MovieBelongingsEntity> {
        return MockMovies.generateMovieBelongingList(10).results.map { it.toEntity() }
    }
}
