package com.example.android_popularmovies.data.store.impl

import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.local.model.MovieDbModel
import com.example.android_popularmovies.data.store.MovieLocalDataStore

class MovieLocalDataStoreImpl(private val movieDao: MovieDao) : MovieLocalDataStore {
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