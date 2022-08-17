package com.example.android_popularmovies.data.source.local

import com.example.android_popularmovies.data.source.MovieLocalDataSource
import com.example.android_popularmovies.data.source.local.database.dao.MovieDao
import com.example.android_popularmovies.data.source.local.database.model.MovieDbModel
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) :
    MovieLocalDataSource {
    override suspend fun getMovies(): List<MovieDbModel> {
        return movieDao.getMovies()
    }

    override suspend fun setMoviesToCache(movies: List<MovieDbModel>) {
        movieDao.addMovies(movies)
    }

    override suspend fun getMovie(movieId: Int): MovieDbModel? {
        return movieDao.getMovie(movieId)
    }
}