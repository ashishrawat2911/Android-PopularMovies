package com.example.android_popularmovies.data.store

import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.local.model.MovieDbModel

class MovieLocalDataStore(private val movieDao: MovieDao) {
    fun getMovies(): List<MovieDbModel> {
        return movieDao.getMovies()
    }

     fun addMovieToCache(movies: List<MovieDbModel>) {
        movieDao.addMovies(movies)
    }

    fun getMovie(movieId: Int): MovieDbModel {
        return movieDao.getMovie(movieId)
    }
}