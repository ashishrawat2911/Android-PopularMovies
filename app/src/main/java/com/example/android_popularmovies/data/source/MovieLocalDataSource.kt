package com.example.android_popularmovies.data.source

import com.example.android_popularmovies.data.source.local.model.MovieDbModel

interface MovieLocalDataSource {
    fun getMovies(): List<MovieDbModel>

    fun addMovieToCache(movies: List<MovieDbModel>)

    fun getMovie(movieId: Int): MovieDbModel
}

