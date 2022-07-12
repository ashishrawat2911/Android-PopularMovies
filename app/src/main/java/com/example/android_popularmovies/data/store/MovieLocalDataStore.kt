package com.example.android_popularmovies.data.store

import com.example.android_popularmovies.data.source.local.model.MovieDbModel

interface MovieLocalDataStore {
    fun getMovies(): List<MovieDbModel>

    fun addMovieToCache(movies: List<MovieDbModel>)

    fun getMovie(movieId: Int): MovieDbModel
}

