package com.example.android_popularmovies.data.source.local

import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.local.model.MovieDbModel
import com.example.android_popularmovies.data.source.MovieLocalDataSource

class MovieLocalDataSourceImpl(private val movieDao: MovieDao) : MovieLocalDataSource {
    override fun getMovies(): List<MovieDbModel> {
        return movieDao.getMovies()
    }

    override fun addMovieToCache(movies: List<MovieDbModel>) {
        movieDao.addMovies(movies)
    }

    override fun getMovie(movieId: Int): MovieDbModel {
        return movieDao.getMovie(movieId)
    }
}