package com.example.android_popularmovies.data.repository

import com.example.android_popularmovies.data.mapper.toDBModel
import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.domain.entity.MovieBelongingsEntity
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val service: MovieApiService,
    private val movieDao: MovieDao,
    private val isNetworkAvailable: Boolean
) : MovieRepository {

    override suspend fun getMovies(): List<MovieEntity> {
        return if (isNetworkAvailable) {
            service.popularMovies()
                .results!!.map { it.toEntity() }.apply {
                    addMovieToCache(this)
                }
        } else {
            movieDao.getMovies().map { it.toEntity() }
        }
    }

    private fun addMovieToCache(movies: List<MovieEntity>) {
            movieDao.addMovies(movies.map { it.toDBModel() })
    }

    override suspend fun getMovieDetails(movieId: Int): MovieEntity {
        return if (isNetworkAvailable) {
            service.movieDetails(movieId).toEntity()
        } else {
            movieDao.getMovie(movieId).toEntity()
        }
    }

    override suspend fun getMovieBelongings(movieId: Int): List<MovieBelongingsEntity> {
        return service.movieBelongings(movieId).results.map { it.toEntity() }
    }
}
